package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class VirtualThreadsDbDemo {

    private static final String DB_URL = "jdbc:h2:mem:virtualthreads;DB_CLOSE_DELAY=-1";
    private static final int POOL_SIZE = 20;
    private static final int TASK_COUNT = 5;
    private static final long OPERATION_DELAY_MS = 300L;

    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(POOL_SIZE);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(3000);
        config.setInitializationFailTimeout(0);
        config.setPoolName("virtual-threads-demo");

        try (HikariDataSource dataSource = new HikariDataSource(config)) {
            setupDatabase(dataSource);

            CountDownLatch ready = new CountDownLatch(TASK_COUNT);
            CountDownLatch start = new CountDownLatch(1);

            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
            List<Future<String>> futures = new ArrayList<>();

            String[] operations = {"insert", "update", "select", "delete", "insert"};

            System.out.println("=== Escenario de prueba ===");
            System.out.println("Pool de conexiones: max=" + POOL_SIZE + ", tareas concurrentes=" + TASK_COUNT);
            printPoolMetrics(dataSource, "Antes de iniciar");

            for (int i = 0; i < TASK_COUNT; i++) {
                final int id = i + 1;
                final String operation = operations[i];
                futures.add(executor.submit(() -> {
                    ready.countDown();
                    start.await();
                    return runOperation(dataSource, id, operation);
                }));
            }

            ready.await();
            System.out.println("Las " + TASK_COUNT + " solicitudes están listas. Se lanzan juntas...");
            start.countDown();

            for (Future<String> future : futures) {
                System.out.println(future.get());
            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);

            printPoolMetrics(dataSource, "Después de terminar");
            printFinalState(dataSource);
            System.out.println("Demo finalizada con pool de conexiones.");
        }
    }

    private static void setupDatabase(HikariDataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS usuarios (
                        id INT PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        saldo INT NOT NULL
                    )
                    """);

            statement.execute("DELETE FROM usuarios");

            for (int i = 1; i <= 4; i++) {
                try (PreparedStatement insert = connection.prepareStatement(
                        "INSERT INTO usuarios(id, nombre, saldo) VALUES (?, ?, ?)") ) {
                    insert.setInt(1, i);
                    insert.setString(2, "usuario-" + i);
                    insert.setInt(3, 1000 + i * 10);
                    insert.executeUpdate();
                }
            }
        }
    }

    private static String runOperation(HikariDataSource dataSource, int id, String operation) throws Exception {
        String message;
        try (Connection connection = dataSource.getConnection()) {
            Thread.sleep(OPERATION_DELAY_MS + (id * 30));
            switch (operation) {
                case "insert" -> {
                    try (PreparedStatement insert = connection.prepareStatement(
                            "INSERT INTO usuarios(id, nombre, saldo) VALUES (?, ?, ?)")) {
                        insert.setInt(1, 100 + id);
                        insert.setString(2, "nuevo-" + id);
                        insert.setInt(3, 500 + id * 10);
                        insert.executeUpdate();
                    }
                    message = "[insert] Hilo virtual " + id + " insertó un nuevo usuario";
                }
                case "update" -> {
                    try (PreparedStatement update = connection.prepareStatement(
                            "UPDATE usuarios SET saldo = saldo + ? WHERE id = ?")) {
                        update.setInt(1, 50 * id);
                        update.setInt(2, id);
                        update.executeUpdate();
                    }
                    message = "[update] Hilo virtual " + id + " actualizó el saldo del usuario " + id;
                }
                case "select" -> {
                    try (PreparedStatement select = connection.prepareStatement(
                            "SELECT nombre, saldo FROM usuarios WHERE id = ?")) {
                        select.setInt(1, id);
                        try (ResultSet resultSet = select.executeQuery()) {
                            if (resultSet.next()) {
                                message = "[select] Hilo virtual " + id + " leyó: " + resultSet.getString("nombre") + ", saldo=" + resultSet.getInt("saldo");
                            } else {
                                message = "[select] Hilo virtual " + id + " no encontró el usuario " + id;
                            }
                        }
                    }
                }
                case "delete" -> {
                    try (PreparedStatement delete = connection.prepareStatement(
                            "DELETE FROM usuarios WHERE id = ?")) {
                        delete.setInt(1, id);
                        delete.executeUpdate();
                    }
                    message = "[delete] Hilo virtual " + id + " eliminó el usuario " + id;
                }
                default -> throw new IllegalArgumentException("Operación desconocida: " + operation);
            }
            return message;
        } catch (SQLException exception) {
            throw new RuntimeException("Error en el hilo " + id + " para operación " + operation, exception);
        }
    }

    private static void printPoolMetrics(HikariDataSource dataSource, String label) {
        var bean = dataSource.getHikariPoolMXBean();
        System.out.println(label + " -> activas=" + bean.getActiveConnections()
                + ", inactivas=" + bean.getIdleConnections()
                + ", esperando=" + bean.getThreadsAwaitingConnection()
                + ", total=" + bean.getTotalConnections());
    }

    private static void printFinalState(HikariDataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM usuarios")) {
            resultSet.next();
            System.out.println("Filas restantes en la tabla usuarios: " + resultSet.getInt("total"));
        }
    }
}
