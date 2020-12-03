package p2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDAOsql implements ProductDAO {
    private Connection conn;
    private OVChipkaartDAO ovdao;

    public ProductDAOsql(Connection conn) {
        this.conn = conn;
    }

    public void setOVChipkaart(OVChipkaartDAO ovdao) {
        this.ovdao = ovdao;
    }

    @Override
    public boolean save(Product product) throws SQLException {

            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs ) VALUES (?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, product.getProduct_nummer());
            preparedStmt.setString(2, product.getNaam());
            preparedStmt.setString(3, product.getBeschrijving());
            preparedStmt.setDouble(4, product.getPrijs());


            // execute the preparedstatement
            preparedStmt.executeUpdate();
            Date datum = new Date(2020 - 10 - 12);
            String status = "dit is de status";
            for (int y = 0; y > product.getOvkaarten().size(); y++) {
                String qr = "INSERT INTO ov_chipkaart_product (kaart_nummer,product_nummer, status, last_update ) VALUES (?,?,?,?)";

                // create the mysql insert preparedstatement
                PreparedStatement prstm = conn.prepareStatement(qr);
                prstm.setInt(1, product.getOvkaarten().get(y).getKaart_nummer());
                prstm.setInt(2, product.getProduct_nummer());
                prstm.setString(3, status);
                prstm.setDate(4, (java.sql.Date) datum);


                // execute the preparedstatement
                preparedStmt.executeUpdate();


            }



        // create a Statement from the connection
        return false;

    }

    @Override
    public boolean update(Product product) throws SQLException {



            String query = "update  product set  naam = ?, beschrijving = ?, prijs = ? where product_nummer = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, product.getNaam());
            preparedStmt.setString(2, product.getBeschrijving());
            preparedStmt.setDouble(3, product.getPrijs());
            preparedStmt.setInt(4, product.getProduct_nummer());

            preparedStmt.executeUpdate();
            String qr = " delete from ov_chipkaart_product where product_nummer = ?";
            PreparedStatement preparedStmt1 = conn.prepareStatement(qr);
            preparedStmt1.setInt(1, product.getProduct_nummer());

            preparedStmt1.executeUpdate();

            Date datum = new Date(2020 - 10 - 12);
            String status = "dit is de status";
            for (int y = 0; y > product.getOvkaarten().size(); y++) {
                String qr1 = "INSERT INTO ov_chipkaart_product (kaart_nummer,product_nummer, status, last_update ) VALUES (?,?,?,?)";

                // create the mysql insert preparedstatement
                PreparedStatement prstm = conn.prepareStatement(qr1);
                prstm.setInt(1, product.getOvkaarten().get(y).getKaart_nummer());
                prstm.setInt(2, product.getProduct_nummer());
                prstm.setString(3, status);
                prstm.setDate(4, (java.sql.Date) datum);


                // execute the preparedstatement
                preparedStmt.executeUpdate();


            }







        return false;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        String query = "delete from  product where product_nummer = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, product.getProduct_nummer());
        preparedStmt.executeUpdate();

        String qy = "delete from  ov_chipkaart_product where product_nummer = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt1 = conn.prepareStatement(qy);
        preparedStmt1.setInt(1, product.getProduct_nummer());
        preparedStmt1.executeUpdate();


        return false;
    }

    @Override
    public List<Product> findByOvkaart(OVChipkaart ovChipkaart) throws SQLException {
        List<Product> producten = new ArrayList<>();

        String query = "select * from  product where product_nummer = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, ovChipkaart.getKaart_nummer());


        try (ResultSet rs = preparedStmt.executeQuery();) {
            while (rs.next()) {
                int product_nummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                String beschrijving = rs.getString("beschrijving");
                Double prijs = rs.getDouble("prijs");
                Product product = new Product(product_nummer, naam, beschrijving, prijs);
                producten.add(product);
                product.addOvchipkaart(ovChipkaart);

            }
            return null;
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {

        PreparedStatement preparedStmt = conn.prepareStatement("select * from Product");
        ResultSet result = preparedStmt.executeQuery();
        List<Product> products = new ArrayList<>();
        while (result.next()) {
            int product_nummer = result.getInt("product_nummer");
            String naam = result.getString("naam");
            String beschrijving = result.getString("beschrijving");
            Double prijs = result.getDouble("prijs");
            Product product = new Product(product_nummer, naam, beschrijving, prijs);
            products.add(product);



        }
    return products;
    }

}

