package org.example.models;


public class Belepes {

    private Long id;


    private Nezo nezo;

    private Meccs meccs;

    private String idopont;

    public Belepes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nezo getNezo() {
        return nezo;
    }

    public void setNezo(Nezo nezo) {
        this.nezo = nezo;
    }

    public Meccs getMeccs() {
        return meccs;
    }

    public void setMeccs(Meccs meccs) {
        this.meccs = meccs;
    }

    public String getIdopont() {
        return idopont;
    }

    public void setIdopont(String idopont) {
        this.idopont = idopont;
    }

    /*public List<Nezo> getAllNezo() {
        List<Nezo> nezoList = new ArrayList<>();
        String sql = "SELECT n.id, n.name, b.id AS belepes_id, b.description AS belepes_description " +
                "FROM nezo n " +
                "LEFT JOIN belepes b ON n.belepes_id = b.id";

        try (Connection conn = DriverManager.getConnection(SQLITE_DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Nezo nezo = new Nezo();
                nezo.setId(rs.getLong("id"));
                nezo.setNev(rs.getString("name"));

                Belepes belepes = new Belepes();
                belepes.setId(rs.getLong("belepes_id"));

                nezo.setBelepesek(Collections.singletonList(belepes));
                nezoList.add(nezo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nezoList;
    }*/

    @Override
    public String toString() {
        return "Belepes{" +
                "id=" + id +
                ", idopont='" + idopont + '\'' +
                '}';
    }
}