package fr.gsb.rv.entites;

public class Medicament {

    String med_depotlegal;
    String med_nomcommercial;
    String fam_code;
    String med_composition;
    String med_effets;
    String med_contreindic;
    double med_prixechantillion;

    public Medicament(){

    }

    public Medicament(String med_depotlegal, String med_nomcommercial, String fam_code, String med_composition, String med_effets, String med_contreindic, double med_prixechantillion) {
        this.med_depotlegal = med_depotlegal;
        this.med_nomcommercial = med_nomcommercial;
        this.fam_code = fam_code;
        this.med_composition = med_composition;
        this.med_effets = med_effets;
        this.med_contreindic = med_contreindic;
        this.med_prixechantillion = med_prixechantillion;
    }


    public String getMed_depotlegal() {
        return med_depotlegal;
    }

    public void setMed_depotlegal(String med_depotlegal) {
        this.med_depotlegal = med_depotlegal;
    }

    public String getMed_nomcommercial() {
        return med_nomcommercial;
    }

    public void setMed_nomcommercial(String med_nomcommercial) {
        this.med_nomcommercial = med_nomcommercial;
    }

    public String getFam_code() {
        return fam_code;
    }

    public void setFam_code(String fam_code) {
        this.fam_code = fam_code;
    }

    public String getMed_composition() {
        return med_composition;
    }

    public void setMed_composition(String med_composition) {
        this.med_composition = med_composition;
    }

    public String getMed_effets() {
        return med_effets;
    }

    public void setMed_effets(String med_effets) {
        this.med_effets = med_effets;
    }

    public String getMed_contreindic() {
        return med_contreindic;
    }

    public void setMed_contreindic(String med_contreindic) {
        this.med_contreindic = med_contreindic;
    }

    public double getMed_prixechantillion() {
        return med_prixechantillion;
    }

    public void setMed_prixechantillion(double med_prixechantillion) {
        this.med_prixechantillion = med_prixechantillion;
    }


    @Override
    public String toString() {
        return this.med_nomcommercial;
    }
}
