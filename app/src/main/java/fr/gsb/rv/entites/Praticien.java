package fr.gsb.rv.entites;

public class Praticien {

    int pra_num;
    String pra_nom;
    String pra_prenom;
    String pra_adresse;
    String pra_cp;
    String pra_ville;
    double pra_coefnotoriete;
    String typ_code;

    public Praticien() {
    }

    public Praticien(int pra_num, String pra_nom, String pra_prenom, String pra_adresse, String pra_cp, String pra_ville, double pra_coefnotoriete, String typ_code) {
        this.pra_num = pra_num;
        this.pra_nom = pra_nom;
        this.pra_prenom = pra_prenom;
        this.pra_adresse = pra_adresse;
        this.pra_cp = pra_cp;
        this.pra_ville = pra_ville;
        this.pra_coefnotoriete = pra_coefnotoriete;
        this.typ_code = typ_code;
    }

    public int getPra_num() {
        return pra_num;
    }

    public void setPra_num(int pra_num) {
        this.pra_num = pra_num;
    }

    public String getPra_nom() {
        return pra_nom;
    }

    public void setPra_nom(String pra_nom) {
        this.pra_nom = pra_nom;
    }

    public String getPra_prenom() {
        return pra_prenom;
    }

    public void setPra_prenom(String pra_prenom) {
        this.pra_prenom = pra_prenom;
    }

    public String getPra_adresse() {
        return pra_adresse;
    }

    public void setPra_adresse(String pra_adresse) {
        this.pra_adresse = pra_adresse;
    }

    public String getPra_cp() {
        return pra_cp;
    }

    public void setPra_cp(String pra_cp) {
        this.pra_cp = pra_cp;
    }

    public String getPra_ville() {
        return pra_ville;
    }

    public void setPra_ville(String pra_ville) {
        this.pra_ville = pra_ville;
    }

    public double getPra_coefnotoriete() {
        return pra_coefnotoriete;
    }

    public void setPra_coefnotoriete(double pra_coefnotoriete) {
        this.pra_coefnotoriete = pra_coefnotoriete;
    }

    public String getTyp_code() {
        return typ_code;
    }

    public void setTyp_code(String typ_code) {
        this.typ_code = typ_code;
    }

    @Override
    public String toString() {
        return "Praticien{" +
                "pra_num=" + pra_num +
                ", pra_nom='" + pra_nom + '\'' +
                ", pra_prenom='" + pra_prenom + '\'' +
                ", pra_adresse='" + pra_adresse + '\'' +
                ", pra_cp='" + pra_cp + '\'' +
                ", pra_ville='" + pra_ville + '\'' +
                ", pra_coefnotoriete=" + pra_coefnotoriete +
                ", typ_code='" + typ_code + '\'' +
                '}';
    }
}
