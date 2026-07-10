package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VirtualThreadsDbDemoThird {

    private static final String DB_URL = "jdbc:h2:mem:virtualthreads-third;DB_CLOSE_DELAY=-1";
    private static final int POOL_SIZE = 15;
    private static final int TASK_COUNT = 6;

    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(POOL_SIZE);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(4000);
        config.setPoolName("virtual-threads-third");

        try (HikariDataSource dataSource = new HikariDataSource(config)) {
            setupDatabase(dataSource);

            CountDownLatch ready = new CountDownLatch(TASK_COUNT);
            CountDownLatch start = new CountDownLatch(1);
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

            for (int i = 0; i < TASK_COUNT; i++) {
                final int id = i + 1;
                executor.submit(() -> {
                    ready.countDown();
                    start.await();
                    return runTask(dataSource, id);
                });
            }

            ready.await();
            System.out.println("Todas las tareas están preparadas. Ejecutando...");
            start.countDown();

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);

            printReport(dataSource);
        }
    }

    private static void setupDatabase(HikariDataSource dataSource) throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS empleados (id INT PRIMARY KEY, nombre VARCHAR(100), cargo VARCHAR(100))");
            statement.execute("DELETE FROM empleados");

            String[] nombres = {"Ana", "Luis", "Marta", "Jose"};
            String[] cargos = {"Admin", "Dev", "QA", "Analista"};

            for (int i = 0; i < nombres.length; i++) {
                try (PreparedStatement ps = connection.prepareStatement("INSERT INTO empleados(id, nombre, cargo) VALUES (?, ?, ?)")) {
                    ps.setInt(1, i + 1);
                    ps.setString(2, nombres[i]);
                    ps.setString(3, cargos[i]);
                    ps.executeUpdate();
                }
            }
        }
    }

    private static String runTask(HikariDataSource dataSource, int id) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Thread.sleep(250L + id * 20);

            try (PreparedStatement ps = connection.prepareStatement("SELECT nombre, cargo FROM empleados WHERE id = ?")) {
                ps.setInt(1, id % 4 + 1);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return "[empleado " + id + "] " + rs.getString("nombre") + " - " + rs.getString("cargo");
                    }
                }
            }
            return "[empleado " + id + "] sin resultado";
        }
    }

    private static void printReport(HikariDataSource dataSource) throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM empleados")) {
            rs.next();
            System.out.println("Total empleados: " + rs.getInt("total"));
            System.out.println("Conexiones activas: " + dataSource.getHikariPoolMXBean().getActiveConnections());
        }
    }
}
