package org.example.services;

import org.example.models.Meccs;
import org.example.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSericeForUpdate {

    private static final String DB_URL = "jdbc:sqlite:db/adatok.sqlite";
    private DatabaseUtil databaseUtil = new DatabaseUtil();

    public static List<Meccs> getAllMeccs() {
        List<Meccs> meccsList = new ArrayList<>();
        String query = "SELECT * FROM meccs";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                meccsList.add(new Meccs(
                        rs.getLong("id"),
                        rs.getString("datum"),
                        rs.getString("kezdes"),
                        rs.getString("tipus"),
                        rs.getInt("belepo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return meccsList;
    }

    public static boolean updateMeccs(Meccs meccs) {
        String query = "UPDATE meccs SET datum = ?, kezdes = ?, belepo = ?, tipus = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, meccs.getDatum());
            pstmt.setString(2, meccs.getKezdes());
            pstmt.setInt(3, meccs.getBelepo());
            pstmt.setString(4, meccs.getTipus());
            pstmt.setLong(5, meccs.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
