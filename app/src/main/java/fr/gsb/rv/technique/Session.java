package fr.gsb.rv.technique;

import fr.gsb.rv.entites.Visiteur;

public class Session {

    private static Session session = null;
    private static Visiteur leVisiteur;


    private Session(Visiteur visiteur){
        leVisiteur = visiteur;
    }

    public static void ouvrir(Visiteur visiteur){
        session = new Session(visiteur);
    }

    public static Session getSession(){
        return session;
    }

    public static Visiteur getLeVisiteur(){
        return leVisiteur;
    }

    public static void fermer(){
        leVisiteur = null;
        session = null;
    }

}
