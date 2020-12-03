package p2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;
    private ReizigerDAO reizigerDAO;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setReizigerDAO(ReizigerDAO reizigerDAO) {
        this.reizigerDAO = reizigerDAO;
    }


    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {

            String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, ovChipkaart.getKaart_nummer());
            preparedStmt.setDate(2, ovChipkaart.getGeldig_tot());
            preparedStmt.setInt(3, ovChipkaart.getKlasse());
            preparedStmt.setDouble(4, ovChipkaart.getSaldo());
            preparedStmt.setInt(5, ovChipkaart.getReiziger_id().getId());


            // execute the preparedstatement
            preparedStmt.executeUpdate();



        // create a Statement from the connection
        return false;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {

            String query = "UPDATE ov_chipkaart SET  geldig_tot = ?,  klasse = ?, saldo = ?, reiziger_id= ? WHERE kaart_nummer = ? ";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDate(1, ovChipkaart.getGeldig_tot());
            preparedStmt.setInt(2, ovChipkaart.getKlasse());
            preparedStmt.setDouble(3, ovChipkaart.getSaldo());
            preparedStmt.setInt(4, ovChipkaart.getReiziger_id().getId());
            preparedStmt.setInt(5, ovChipkaart.getKaart_nummer());


            // execute the preparedstatement
            preparedStmt.executeUpdate();


        // create a Statement from the connection
        return false;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {

            String query = "DELETE from ov_chipkaart WHERE kaart_nummer = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, ovChipkaart.getKaart_nummer());

            // execute the preparedstatement
            preparedStmt.executeUpdate();
            System.out.println("Person removed from database...");


        } catch (Exception e) {
            System.err.println("Got an exception!");

        }
        // create a Statement from the connection
        return false;

    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        String sql = "select * from ov_chipkaart where reiziger_id= ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, reiziger.getId());
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    int kaart_nummer = rs.getInt("kaart_nummer");
                    Date geldig_tot = rs.getDate("geldig_tot");
                    int klasse = rs.getInt("klasse");
                    Double saldo = rs.getDouble("saldo");
                    int reiziger_id = rs.getInt("reiziger_id");
                    new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reizigerDAO.findById(reiziger_id));


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
    public OVChipkaart findById(int kaartnummer) {
        String sql = "select * from ov_chipkaart where kaart_nummer = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, kaartnummer);
            try (ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    Date geldig_tot = rs.getDate("geldig_tot");
                    int klasse = rs.getInt("klasse");
                    Double saldo = rs.getDouble("saldo");
                    int reiziger_id = rs.getInt("reiziger_id");
                    return new OVChipkaart(kaartnummer, geldig_tot, klasse, saldo, reizigerDAO.findById(reiziger_id));


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
    public List<OVChipkaart> findAll() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM ov_chipkaart");
        List<OVChipkaart> ovChipkaarts = new ArrayList<>();
        while (result.next()) {
            int kaart_nummer = result.getInt("kaart_nummer");
            Date geldig_tot  = result.getDate("geldig_tot");
            int klasse = result.getInt("klasse");
            Double saldo = result.getDouble("saldo");;
            int reiziger_id = result.getInt("reiziger_id");

            OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer, geldig_tot, klasse, saldo, reizigerDAO.findById(reiziger_id));
            ovChipkaarts.add(ovChipkaart);

        }
        return ovChipkaarts;

    }
}
