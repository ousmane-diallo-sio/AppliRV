package fr.gsb.rv.technique;

import fr.gsb.rv.entites.Visiteur;

public class Session {

    private static Session session = null;
    private Visiteur leVisiteur;

    private Session(Visiteur visiteur){
        this.leVisiteur = visiteur;
    }

    public static Session getSession(){
        return session;
    }

    public void ouvrir(Visiteur visiteur){
        session = new Session(visiteur);
    }

    public void fermer(){
        this.leVisiteur = null;
        session = null;
    }

}
