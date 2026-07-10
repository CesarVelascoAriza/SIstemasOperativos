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

public class VirtualThreadsDbDemoAlternative {

    private static final String DB_URL = "jdbc:h2:mem:virtualthreads-alt;DB_CLOSE_DELAY=-1";
    private static final int POOL_SIZE = 10;
    private static final int TASK_COUNT = 4;

    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(POOL_SIZE);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(2000);
        config.setPoolName("virtual-threads-alt");

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
            System.out.println("Las tareas están listas. Iniciando...");
            start.countDown();

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);

            printSummary(dataSource);
        }
    }

    private static void setupDatabase(HikariDataSource dataSource) throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS productos (id INT PRIMARY KEY, nombre VARCHAR(100), stock INT)");
            statement.execute("DELETE FROM productos");

            for (int i = 1; i <= 3; i++) {
                try (PreparedStatement ps = connection.prepareStatement("INSERT INTO productos(id, nombre, stock) VALUES (?, ?, ?)")) {
                    ps.setInt(1, i);
                    ps.setString(2, "producto-" + i);
                    ps.setInt(3, 100 + i);
                    ps.executeUpdate();
                }
            }
        }
    }

    private static String runTask(HikariDataSource dataSource, int id) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Thread.sleep(200L + id * 50);

            try (PreparedStatement ps = connection.prepareStatement("SELECT stock FROM productos WHERE id = ?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int stock = rs.getInt("stock");
                        return "[tarea " + id + "] stock leído: " + stock;
                    }
                }
            }
            return "[tarea " + id + "] no encontró producto";
        }
    }

    private static void printSummary(HikariDataSource dataSource) throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS total FROM productos")) {
            rs.next();
            System.out.println("Total de productos: " + rs.getInt("total"));
            System.out.println("Conexiones activas: " + dataSource.getHikariPoolMXBean().getActiveConnections());
        }
    }
}
