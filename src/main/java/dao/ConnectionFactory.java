package dao;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class ConnectionFactory {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = getEnv("DB_URL", "jdbc:mysql://127.0.0.1:3306/dbteste?useTimezone=true&serverTimezone=UTC");
    private static final String USER = getEnv("DB_USER", "app");
    private static final String PASS = getEnv("DB_PASS", "app123");
	
    private static HikariDataSource dataSource;

    static {
        try {
            Class.forName(DRIVER);
            initializeHikariCP();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar driver MySQL: ", e);
        }
    }

    private static void initializeHikariCP() {
        HikariConfig config = new HikariConfig();
        //Min pool size: 5 (connections ready) - Max pool size: 20 (handles peak load) - Connection timeout: 30 seconds
        //Idle timeout: 10 minutes - Max lifetime: 30 minutes
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASS);
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setAutoCommit(true);
        
        dataSource = new HikariDataSource(config);
        System.out.println("✓ HikariCP pool inicializado com sucesso (Min: 5, Max: 20)");
        
        // Shutdown hook para graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
                System.out.println("✓ HikariCP pool fechado");
            }
        }));
    }
    
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter conexão do pool: ", e);
        }
    }

    private static String getEnv(String key, String fallback) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value;
    }

    public static void printPoolStats() {
        if (dataSource != null) {
            System.out.println("Pool Stats - Active: " + dataSource.getHikariPoolMXBean().getActiveConnections() 
                + ", Idle: " + dataSource.getHikariPoolMXBean().getIdleConnections()
                + ", Total: " + dataSource.getHikariPoolMXBean().getTotalConnections());
        }
    }
}
