package fr.gsb.rv.entites;

public class RapportVisite {

    int rap_num;
    String rap_bilan;
    String pra_cp;
    String pra_nom;
    String rap_date_visite;
    String pra_prenom;
    String pra_ville;


    public RapportVisite(){

    }

    public RapportVisite(int rap_num, String rap_bilan, String pra_cp, String pra_nom, String rap_date_visite, String pra_prenom, String pra_ville){
        this.rap_num = rap_num;
        this.rap_bilan = rap_bilan;
        this.pra_cp = pra_cp;
        this.pra_nom = pra_nom;
        this.rap_date_visite = rap_date_visite;
        this.pra_prenom = pra_prenom;
        this.pra_ville = pra_ville;
    }


    public String getRap_bilan() {
        return rap_bilan;
    }

    public void setRap_bilan(String rap_bilan) {
        this.rap_bilan = rap_bilan;
    }

    public String getPra_cp() {
        return pra_cp;
    }

    public void setPra_cp(String pra_cp) {
        this.pra_cp = pra_cp;
    }

    public int getRap_num() {
        return rap_num;
    }

    public void setRap_num(int rap_num) {
        this.rap_num = rap_num;
    }

    public String getPra_nom() {
        return pra_nom;
    }

    public void setPra_nom(String pra_nom) {
        this.pra_nom = pra_nom;
    }

    public String getRap_date_visite() {
        return rap_date_visite;
    }

    public void setRap_date_visite(String rap_date_visite) {
        this.rap_date_visite = rap_date_visite;
    }

    public String getPra_prenom() {
        return pra_prenom;
    }

    public void setPra_prenom(String pra_prenom) {
        this.pra_prenom = pra_prenom;
    }

    public String getPra_ville() {
        return pra_ville;
    }

    public void setPra_ville(String pra_ville) {
        this.pra_ville = pra_ville;
    }


    @Override
    public String toString() {
        return "RapportVisite{" +
                "rap_bilan='" + rap_bilan + '\'' +
                ", pra_cp='" + pra_cp + '\'' +
                ", rap_num=" + rap_num +
                ", pra_nom='" + pra_nom + '\'' +
                ", rap_date_visite='" + rap_date_visite + '\'' +
                ", pra_prenom='" + pra_prenom + '\'' +
                ", pra_ville='" + pra_ville + '\'' +
                '}';
    }
}
