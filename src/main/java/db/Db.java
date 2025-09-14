package db;

import java.sql.*;

public class Db {
    private static final String URL  = "jdbc:h2:file:./shopmate;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    static {
        try { runMigrations(); }
        catch (Exception e) { throw new RuntimeException("DB migration failed", e); }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private static void runMigrations() throws SQLException {
        try (Connection c = get(); Statement s = c.createStatement()) {
            s.execute("""
                CREATE TABLE IF NOT EXISTS products(
                  id INT PRIMARY KEY,
                  name VARCHAR(200) NOT NULL,
                  price DECIMAL(10,2) NOT NULL,
                  image_path VARCHAR(255)
                );
            """);

            s.execute("""
                CREATE TABLE IF NOT EXISTS orders(
                  id IDENTITY PRIMARY KEY,
                  customer_name VARCHAR(200) NOT NULL,
                  address VARCHAR(200) NOT NULL,
                  city VARCHAR(100) NOT NULL,
                  zip VARCHAR(20) NOT NULL,
                  subtotal DECIMAL(10,2) NOT NULL,
                  tax DECIMAL(10,2) NOT NULL,
                  total DECIMAL(10,2) NOT NULL,
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
            """);

            s.execute("""
                CREATE TABLE IF NOT EXISTS order_items(
                  order_id BIGINT NOT NULL,
                  product_id INT NOT NULL,
                  quantity INT NOT NULL,
                  price_each DECIMAL(10,2) NOT NULL,
                  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                  FOREIGN KEY (product_id) REFERENCES products(id)
                );
            """);

            // seed once
            try (ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM products")) {
                rs.next();
                if (rs.getInt(1) == 0) {
                    s.execute("""
                        INSERT INTO products(id,name,price,image_path) VALUES
                          (1,'Laptop 14',1299.00,'laptop14.jpg'),
                          (2,'Noise-Cancel Headphones',179.00,'headphones.jpg'),
                          (3,'Iphone 15',899.00,'iphone15.jpg'),
                          (4,'Mechanical Keyboard',89.00,'keyboard.jpg'),
                          (5,'USB-C Dock',129.00,'dock.jpg'),
                          (6,'4K Monitor 27"',349.00,'monitor.jpg');
                    """);
                }
            }
        }
    }
}
