package org.example.services;

import org.example.models.DataRow;
import org.example.models.Meccs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private static final String DB_URL = "jdbc:sqlite:db/adatok.sqlite";

    public boolean connect() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Belépés lekérdezése
    public List<DataRow> get100DataRow() {
        System.out.println("Adatbázis kapcsolata: " + connect());
        String query = "SELECT * FROM belepes " +
                "INNER JOIN nezo ON belepes.nezoid = nezo.id " +
                "INNER JOIN meccs ON belepes.meccsid = meccs.id ";
        List<DataRow> resultList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                DataRow row = new DataRow(
                        rs.getInt("id"),
                        rs.getInt("nezoId"),
                        rs.getInt("meccsId"),
                        rs.getString("idopont"),
                        rs.getString("nev"),
                        rs.getBoolean("ferfi"),
                        rs.getBoolean("berletes"),
                        rs.getBoolean("ferfi_boolean"),
                        rs.getBoolean("berletes_boolean"),
                        rs.getString("datum"),
                        rs.getString("kezdes"),
                        rs.getInt("belepo"),
                        rs.getString("tipus")
                );
                resultList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    // Meccsek lekérdezése
    public List<Meccs> getAllMatches() {
        System.out.println("Adatbázis kapcsolata: " + connect());
        List<Meccs> matches = new ArrayList<>();
        String sql = "SELECT id, datum, kezdes, tipus, belepo FROM meccs";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("Found match: " + rs.getLong("id"));
                Meccs meccs = new Meccs();
                meccs.setId(rs.getLong("id"));
                meccs.setDatum(rs.getString("datum"));
                meccs.setKezdes(rs.getString("kezdes"));
                meccs.setTipus(rs.getString("tipus"));
                meccs.setBelepo(rs.getInt("belepo"));
                matches.add(meccs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches;
    }

    public List<DataRow> getFilteredMatches(String idopont, String date, String dateTime, String nezoNev, boolean isFerfi, boolean berletes, String type, int belepoSzam) {
        List<DataRow> dataRows = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM belepes " +
                "                INNER JOIN nezo ON belepes.nezoid = nezo.id" +
                "                INNER JOIN meccs ON belepes.meccsid = meccs.id WHERE 1=1");

        if (idopont != null && !idopont.isEmpty()) {
            sql.append(" AND idopont Like '%").append(idopont).append("%'");
        }
        if (date != null && !date.isEmpty()) {
            sql.append(" AND datum LIKE '%").append(date.toLowerCase()).append("%'");
        }
        if (dateTime != null && !dateTime.isEmpty()) {
            sql.append(" AND kezdes LIKE '%").append(dateTime.toLowerCase()).append("%'");
        }
        if (nezoNev != null && !nezoNev.isEmpty()) {
            sql.append(" AND nev LIKE '%").append(nezoNev).append("%'");
        }
        if(isFerfi) {
            sql.append(" AND ferfi = -1");
        }else {
            sql.append(" AND ferfi = 0");
        }
        if (berletes){
            sql.append(" AND berletes = -1");
        } else {
            sql.append(" AND berletes = 0");
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND tipus = '").append(type.toLowerCase()).append("'");
        }
        if (belepoSzam != 0) {
            sql.append(" AND belepo = ").append(belepoSzam);
        }

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {

            while (rs.next()) {
                DataRow row = new DataRow(
                        rs.getInt("id"),
                        rs.getInt("nezoId"),
                        rs.getInt("meccsId"),
                        rs.getString("idopont"),
                        rs.getString("nev"),
                        rs.getBoolean("ferfi"),
                        rs.getBoolean("berletes"),
                        rs.getBoolean("ferfi_boolean"),
                        rs.getBoolean("berletes_boolean"),
                        rs.getString("datum"),
                        rs.getString("kezdes"),
                        rs.getInt("belepo"),
                        rs.getString("tipus")
                );
                dataRows.add(row);
            }
            return dataRows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Új rekord hozzáadása
    public boolean addRecord(DataRow dataRow) {
        //todo: belépés, meccs és nézőkre 1 külön insertek létrehozása
        //String insertNezoSql = "INSERT INTO nezo (id, nev, ferfi, berletes, ferfi_boolean, berletes_boolean) VALUES (?, ?, ?, ?, ?, ?)";
        //String inserMeccsSql = "INSERT INTO meccs (id, datum, kezdes, tipus, belepo) VALUES (?, ?, ?, ?, ?)";
        String insertbelepesSql = "INSERT INTO belepes (id, nezoId, meccsId, idopont) VALUES (?, ?, ?, ?)";

        //String lastMeccsIdSql = "select id from meccs order by id Desc limit 1";
        //String lastNezoIdSql = "select id from nezo order by id Desc limit 1";
        String lastBelepesIdSql = "select id from belepes order by id Desc limit 1";
        String getMeccsIdByDatumIdoSql = "select id from meccs where datum = ? and kezdes = ?";
        String getNezoIdByNezoNevIsferfiSql = "select id from nezo where nev = ? and ferfi = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            long lastBelepesId = getLastId(conn, lastBelepesIdSql);
            dataRow.setMeccsId(getMeccsIdByDatumIdo(dataRow.getDatum(), dataRow.getKezdes(), conn, getMeccsIdByDatumIdoSql));
            dataRow.setNezoId(getNezoIdByNezoNevIsferfiSql(dataRow.getNev(), dataRow.isFerfi()?"-1":"0", conn, getNezoIdByNezoNevIsferfiSql));

            // insert folyamat végrehajtása
            //PreparedStatement meccsPstmt = getMeccsPreparedStatement(dataRow, conn, inserMeccsSql, lastMeccsId);
            //PreparedStatement nezoPstmt = getNezoPreparedStatement(dataRow, conn, insertNezoSql, lastNezoId);
            PreparedStatement belepesPstmt = getBelepesPreparedStatement(dataRow, conn, insertbelepesSql, lastBelepesId);

            //boolean meccsRowsAffected = meccsPstmt.execute();
            //boolean nezoRowsAffected = nezoPstmt.execute();
            boolean belepesRowsAffected = belepesPstmt.execute();
            if (!belepesRowsAffected){
                addPlusOneBelepoForMeccs(conn, dataRow.getMeccsId());
            }

            //meccsPstmt.close();
            //nezoPstmt.close();
            belepesPstmt.close();
            conn.close();
            return !belepesRowsAffected;//ha false akkor nincs hiba
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addPlusOneBelepoForMeccs(Connection conn, long meccsId) throws SQLException {
        String sql = "Update meccs set belepo = belepo+1 where id = ?";
        PreparedStatement belepesPstmt = conn.prepareStatement(sql);
        belepesPstmt.setLong(1, meccsId);

        belepesPstmt.executeUpdate();
        belepesPstmt.close();
    }

    private long getNezoIdByNezoNevIsferfiSql(String nev, String ferfi, Connection conn, String getNezoIdByNezoNevIsferfiSql) throws SQLException {
        PreparedStatement pstmtNezo = conn.prepareStatement(getNezoIdByNezoNevIsferfiSql.toString());
        pstmtNezo.setString(1, nev);
        pstmtNezo.setString(2, ferfi);
        ResultSet rs = pstmtNezo.executeQuery();
        if (rs.next()) {
            return rs.getLong(1);
        }
        throw new SQLException();
    }

    private long getMeccsIdByDatumIdo(String datum, String kezdes, Connection conn, String getMeccsIdByDatumIdoSql) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(getMeccsIdByDatumIdoSql);
        pstmt.setString(1, datum);
        pstmt.setString(2, kezdes);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getLong(1);
        }
        throw new SQLException();
    }

    private PreparedStatement getBelepesPreparedStatement(DataRow dataRow, Connection conn, String insertbelepesSql, long lastBelepesId) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement(insertbelepesSql.toString());
        pstmt.setLong(1, lastBelepesId+1);
        pstmt.setLong(2, dataRow.getNezoId());
        pstmt.setLong(3, dataRow.getMeccsId());
        pstmt.setString(4, dataRow.getIdopont());

        return pstmt;
    }

    private PreparedStatement getNezoPreparedStatement(DataRow dataRow, Connection conn, String insertNezoSql, long lastNezoId) throws SQLException{
        PreparedStatement pstmt = conn.prepareStatement(insertNezoSql.toString());
        pstmt.setLong(1, lastNezoId + 1);
        pstmt.setString(2, dataRow.getNev());
        pstmt.setBoolean(3, dataRow.isFerfi());
        pstmt.setBoolean(4, dataRow.isBerletes());
        pstmt.setBoolean(5, dataRow.isFerfiBoolean());
        pstmt.setBoolean(6, dataRow.isBerletesBoolean());
        return pstmt;
    }

    private static PreparedStatement getMeccsPreparedStatement(DataRow dataRow, Connection conn, String inserMeccsSql, long lastMeccsId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(inserMeccsSql.toString());
        pstmt.setLong(1, lastMeccsId + 1);
        pstmt.setString(2, dataRow.getDatum());
        pstmt.setString(3, dataRow.getKezdes());
        pstmt.setString(4, dataRow.getTipus());
        pstmt.setInt(5, dataRow.getBelepo());
        return pstmt;
    }

    private long getLastId(Connection conn, String lastMeccsIdSql) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(lastMeccsIdSql.toString());
            ResultSet rs = preparedStatement.executeQuery();
            long lastId = 0l;
            while (rs.next()) {
                lastId = rs.getLong("id");
            }
            rs.close();
            preparedStatement.close();
            return lastId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Rekord frissítése
    public boolean updateRecord(Long id, String datum, String kezdes, int belepo) {
        String sql = "UPDATE meccs SET datum = ?, kezdes = ?, belepo = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, datum);
            pstmt.setString(2, kezdes);
            pstmt.setInt(3, belepo);
            pstmt.setLong(4, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Rekord törlése
    public boolean deleteRecord(Long id) {
        String sql = "DELETE FROM belepes WHERE id = " + id;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> fechUnikNezok() {
        List<String> nezok = new ArrayList<>();
        String query = "SELECT DISTINCT * FROM nezo";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                nezok.add(rs.getString("nev"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nezok;
    }

        public List<String> fetchUniqueDates() {
        List<String> uniqueDates = new ArrayList<>();
        String query = "SELECT DISTINCT * FROM meccs";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                uniqueDates.add(rs.getString("datum"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uniqueDates;
    }

    public List<String> fetchTimesForDate(String date) {
        List<String> times = new ArrayList<>();
        String query = "SELECT kezdes FROM meccs WHERE datum = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, date);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    times.add(rs.getString("kezdes"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return times;
    }

    public boolean fechUnikNezoIsFerfi(String value) {
        String userIsFerfi = null;
        String query = "SELECT ferfi FROM nezo WHERE nev = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    userIsFerfi = rs.getString("ferfi");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userIsFerfi.equals("-1");

    }

    public boolean fechUnikNezoIsBerletes(String value) {
        String userIsBerletes = "false";
        String query = "SELECT berletes FROM nezo WHERE nev = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    userIsBerletes = rs.getString("berletes");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userIsBerletes.equals("-1");

    }
}