package fr.gsb.rv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class RechercheRvActivity extends AppCompatActivity {

    String logTag;
    TableLayout tlMois;
    TableLayout tlAnnee;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_rv);

        this.logTag = "GSB_RECHERCHE_ACTIVITY";

        tlMois = findViewById(R.id.tlMois);
        String[] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        for( int i=0; i < mois.length; i++ ){
            TextView tv = new TextView(this);
            tv.setText(mois[i]);
            tv.setWidth(tlMois.getWidth());
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(30);
            tlMois.addView(tv);
        }

        tlAnnee = findViewById(R.id.tlAnnee);
        int anneeCourante = LocalDate.now().getYear();
        List<String> annees = new ArrayList<String>();
        Log.i(this.logTag, "Annee Courante : " + anneeCourante);
        for(int i=0; i<5; i++){
            annees.add( String.valueOf( anneeCourante - i ) );
            Log.i(this.logTag, "Année n°" + i + " : " + String.valueOf( anneeCourante - i ));
        }

        for(int i=0; i<annees.size(); i++){
            TextView tv = new TextView(this);
            tv.setText(annees.get(i));
            tv.setWidth(tlAnnee.getWidth());
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTextSize(30);
            tlAnnee.addView(tv);
        }



    }
}