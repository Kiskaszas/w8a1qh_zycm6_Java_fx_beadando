package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    // SQLite adatbázis URL
    private static final String SQLITE_DB_URL = "jdbc:sqlite:adatok.sqlite";

    // Kapcsolódás az SQLite adatbázishoz
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(SQLITE_DB_URL);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to SQLite database: " + e.getMessage(), e);
        }
    }

    // Segédfüggvény a kapcsolat teszteléséhez
    public static void testConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Kapcsolódás sikeres az adatbázishoz: " + SQLITE_DB_URL);
            }
        } catch (SQLException e) {
            System.err.println("Kapcsolódási hiba: " + e.getMessage());
        }
    }

}