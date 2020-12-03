package p2;

import p2.Adres;
import p2.AdresDAO;
import p2.Reiziger;
import p2.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn;
    private ReizigerDAO reizigerDAO;


    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setReizigerDAO(ReizigerDAO reizigerDAO) {
        this.reizigerDAO = reizigerDAO;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {

            String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?,?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, adres.getId());
            preparedStmt.setString (2, adres.getPostcode());
            preparedStmt.setString(3, adres.getHuisnummer());
            preparedStmt.setString(4, adres.getStraat());
            preparedStmt.setString(5,adres.getWoonplaats());
            preparedStmt.setInt (6, adres.getReiziger_id().getId());

            // execute the preparedstatement
            preparedStmt.executeUpdate();



        // create a Statement from the connection
        return false;

    }

    @Override
    public boolean update(Adres adres) throws SQLException {

            String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ? ";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, adres.getPostcode());
            preparedStmt.setString(2,adres.getHuisnummer());
            preparedStmt.setString(3,adres.getStraat());
            preparedStmt.setString(4,adres.getWoonplaats());
            preparedStmt.setInt (5, adres.getReiziger_id().getId());
            preparedStmt.setInt (6, adres.getId());



            // execute the preparedstatement
            preparedStmt.executeUpdate();

        // create a Statement from the connection
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        try{

            String query = "DELETE from adres WHERE adres_id = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, adres.getId());

            // execute the preparedstatement
            preparedStmt.executeUpdate();
            System.out.println("Person removed from database...");


        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");

        }
        // create a Statement from the connection
        return false;

    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        String sql = "select * from adres where reiziger_id=?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, reiziger.getId());
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    int adres_id = rs.getInt("adres_id");
                    String postcode  = rs.getString("postcode");
                    String huisnummer = rs.getString("huisnummer");
                    String straat = rs.getString("straat");
                    String woonplaats = rs.getString("woonplaats");
                    int reiziger_id = rs.getInt("reiziger_id");
                    System.out.println(postcode + huisnummer+straat+ woonplaats+reiziger_id);
                    return new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger);


                }
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM adres");
        ArrayList<Adres> adressen = new ArrayList<>();
        while (result.next()) {
            int adres_id = result.getInt("adres_id");
            String postcode  = result.getString("postcode");
            String huisnummer = result.getString("huisnummer");
            String straat = result.getString("straat");
            String woonplaats = result.getString("woonplaats");
            int reiziger_id = result.getInt("reiziger_id");

            Adres adres = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reizigerDAO.findById(reiziger_id));
            adressen.add(adres);

        }
        return adressen;


    }
}
