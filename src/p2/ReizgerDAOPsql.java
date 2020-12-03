package p2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReizgerDAOPsql implements ReizigerDAO {
    private Connection conn;
    private AdresDAO adao;

    public ReizgerDAOPsql(Connection conn) {
        this.conn = conn;
    }
    public void setAdresDAO(AdresDAO adao){
        this.adao = adao;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {

        String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?,?,?,?,?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, reiziger.getId());
        preparedStmt.setString(2, reiziger.getVoorletters());
        preparedStmt.setString(3, reiziger.getTussenvoegsel());
        preparedStmt.setString(4, reiziger.getAchternaam());
        preparedStmt.setDate(5, reiziger.getGeboortedatum());

        // execute the preparedstatement
        preparedStmt.executeUpdate();


        // create a Statement from the connection
        return false;

    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {

            String query = "UPDATE reiziger SET  voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ? ";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, reiziger.getVoorletters());
            preparedStmt.setString(2,reiziger.getTussenvoegsel());
            preparedStmt.setString(3,reiziger.getAchternaam());
            preparedStmt.setDate(4,reiziger.getGeboortedatum());
            preparedStmt.setInt (5, reiziger.getId());


            // execute the preparedstatement
            preparedStmt.executeUpdate();



        // create a Statement from the connection
        return false;

    }


    @Override
    public boolean delete(Reiziger reiziger) {
        try{

            String query = "DELETE from reiziger WHERE reiziger_id = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, reiziger.getId());

            // execute the preparedstatement
            preparedStmt.executeUpdate();
            System.out.println("Person removed from database...");


        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        // create a Statement from the connection
        return false;

    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        String sql = "select * from reiziger where reiziger_id=?";
        Reiziger reiziger;

        try (
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String voorletters = rs.getString("voorletters");
                    String tussenvoegsel = rs.getString("tussenvoegsel");
                    String achternaam = rs.getString("achternaam");
                    java.sql.Date geboortedatum = rs.getDate("geboortedatum");



                     reiziger = new Reiziger (id, voorletters, tussenvoegsel, achternaam,geboortedatum);
                     return reiziger;

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }





        return null;

    }


    

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        String sql = "select * from reiziger where geboortedatum=?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setDate(1, Date.valueOf(datum));
            try (ResultSet rs = stmt.executeQuery();) {
                ArrayList<Reiziger>reizigers = new ArrayList<>();
                while (rs.next()) {
                    System.out.println("reiziger_id=" + rs.getString(1));
                    System.out.println("voorletters=" + rs.getString(2));
                    System.out.println("tussenvoegsel=" + rs.getString(3));
                    System.out.println("achternaam=" + rs.getString(4));
                    System.out.println("geboortdatum=" + rs.getDate(5));
                }
                return reizigers;
            }
        }



    }

    @Override
    public List<Reiziger> findAll() throws SQLException {

        // create a Statement from the connection
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM reiziger");
        ArrayList<Reiziger>reizigers = new ArrayList<>();
        while (rs.next()) {
            int reiziger_id = rs.getInt("reiziger_id");
            String voorletters  = rs.getString("voorletters");
            String tussenvoegsel = rs.getString("tussenvoegsel");
            String achternaam = rs.getString("achternaam");
            Date geboortedatum = rs.getDate("geboortedatum");


            Reiziger reiziger = new Reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum);
            adao.findByReiziger(reiziger);
                    reizigers.add(reiziger);






        }
        rs.close();
        stmt.close();
return reizigers;

    }
}
