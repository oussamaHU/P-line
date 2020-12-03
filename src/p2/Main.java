package p2;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] arg) throws SQLException {
        getConnection();

        ReizgerDAOPsql rdao = new ReizgerDAOPsql(connection);
        AdresDAOPsql adao = new AdresDAOPsql(connection);
        OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(connection);
        ProductDAOsql pdao = new ProductDAOsql(connection);
        rdao.setAdresDAO(adao);
        adao.setReizigerDAO(rdao);
        ovdao.setReizigerDAO(rdao);
        pdao.setOVChipkaart(ovdao);
        testReizigerDAO(rdao);
        testAdresDAO(adao, rdao);
        testOvchipkaartDAO(rdao, ovdao);
        testProductDAO(pdao, ovdao);
//        Reiziger reiziger = rdao.findById(5);
//        System.out.println(reiziger);

    }

    private static void getConnection() {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Oussama100";

        try {


            connection = DriverManager.getConnection(url);


        } catch (SQLException sqlex) {
            System.err.println("error, connectie fout" + sqlex.getMessage());
        }
    }

    private static void closeConnection() throws SQLException {
        connection.close();

    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1999-03-14";
        Reiziger oussama = new Reiziger(80, "Y", null, "vandb", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        //rdao.save(oussama);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
//
//        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("voor update:");
        System.out.println(reizigers.stream().
                filter(reiziger -> oussama.getId() == reiziger.getId()).findFirst().
                orElse(null) + " reizigers\n");
        oussama.setVoorletters("Q");
        oussama.setTussenvoegsel("");
        oussama.setAchternaam("VANYAHYA");
        oussama.setGeboortedatum(Date.valueOf("2020-12-10"));
        rdao.update(oussama);
        System.out.println("[Test]");
        reizigers = rdao.findAll();
        System.out.println("na update:");
        System.out.println(reizigers.stream().filter(reiziger -> oussama.getId() == reiziger.getId()).findFirst().orElse(null) + " reizigers\n");

//
////         rdao.delete(o);
////        System.out.println(sietske);
////        System.out.println("TEST");
//
//        rdao.findById(5);
//
//        rdao.findByGbdatum("2002-12-03");


    }

    private static void testAdresDAO(AdresDAO adao, ReizgerDAOPsql rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle reizigers op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String woonplaats = "Groningen";
        String gbdatum = "1999-03-14";
        Reiziger oussama = new Reiziger(80, "Y", null, "vandb", java.sql.Date.valueOf(gbdatum));
        Adres adres = new Adres(7, "3561KA", "36", "vanyahyastraat", "Groningen", oussama);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
//        rdao.save(oussama);
//        adao.save(adres);
        oussama.setAdres(adres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        rdao.findById(80);

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
//        adres.setPostcode("Q");
//        adres.setHuisnummer("12");
//        adres.setStraat("vanJanstraat");
//        adres.setWoonplaats("Utrecht");
//        adao.update(adres);
//        System.out.println("[Test]");


        //    adao.delete(adres);
//        System.out.println(sietske);
//        System.out.println("TEST");
        Adres adress = adao.findByReiziger(oussama);
        System.out.println(adress);


    }

    private static void testOvchipkaartDAO(ReizgerDAOPsql rdao, OVChipkaartDAOPsql ovdao) throws SQLException {
        System.out.println("\n---------- Test OvchipkaartDAO -------------");

        // Haal alle reizigers op uit de database
        List<OVChipkaart> ovChipkaarts = ovdao.findAll();
        System.out.println("[Test] OvchipkaartDAO.findAll() geeft de volgende adressen:");
        for (OVChipkaart ov : ovChipkaarts) {
            System.out.println(ov);
        }
        System.out.println();
        String geldig_tot = "2021-03-14";
        String gbdatum = "1999-03-14";
        Reiziger testouss = new Reiziger(105, "a", null, "heren", java.sql.Date.valueOf(gbdatum));
        //rdao.save(testouss);

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        OVChipkaart ov = new OVChipkaart(45, java.sql.Date.valueOf(geldig_tot), 2, 2.00, testouss);
        //ovdao.save(ov);
        System.out.print("[Test] Eerst " + ovChipkaarts.size() + " ovchipkaarten, na ovchipkaartDAO.save() ");
        //ovdao.delete(ov);

//       ov.setSaldo(5.00);
//       ovdao.update(ov);
        System.out.println(ov);
        //ovdao.findByReiziger(testouss);
        System.out.println(testouss);

        ovdao.findById(35283);
        System.out.println(ovdao.findById(35283));


    }

    private static void testProductDAO(ProductDAOsql pdao, OVChipkaartDAOPsql ovdao) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------");

        // Haal alle reizigers op uit de database
        List<Product> products = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println();

        Product product = new Product(1001, "test", "test", 1.00);
//pdao.save(product);
        System.out.println(product);

        product.setNaam("ouderenov");
//        pdao.update(product);
        System.out.println(product);

//        pdao.delete(product);


    }
}
