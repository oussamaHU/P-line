package p2;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<OVChipkaart> Ovkaarten = new ArrayList<>();

    public List<OVChipkaart> getOvkaarten() {
        return Ovkaarten;
    }

    public void setOvkaarten(List<OVChipkaart> ovkaarten) {
        Ovkaarten = ovkaarten;
    }

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }


    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }
    public void addOvchipkaart(OVChipkaart ovchipkaart){
        Ovkaarten.add(ovchipkaart);
    }
}


