package fr.gsb.rv;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RechercheRvActivity extends AppCompatActivity {

    String logTag;

    String[] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};

    Map<String, String> listeMois = new HashMap<String, String>();

    Spinner spinnerMois;
    Spinner spinnerAnnee;

    String numMoisCourant;
    String moisCourant;
    String anneeCourante;

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_rv);

        this.logTag = "GSB_RECHERCHE_ACTIVITY";

        this.spinnerMois = findViewById(R.id.spinnerMois);
        ArrayAdapter<String> moisArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mois);
        moisArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerMois.setAdapter(moisArrayAdapter);
        this.spinnerMois.setSelection(LocalDate.now().getMonthValue() -1);

        this.spinnerMois.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                moisCourant = mois[position];
                String before = "";
                if(position < 10){
                    before = "0";
                }
                numMoisCourant = String.valueOf(before + (position +1));
                Log.i(logTag, "Mois sélectionné : " + moisCourant);
                Log.i(logTag, "Num mois sélectionné : " + numMoisCourant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.spinnerAnnee = findViewById(R.id.spinnerAnnee);

        int anneeActuelle = LocalDate.now().getYear();
        List<String> annees = new ArrayList<String>();
        Log.i(logTag, "Annee Courante : " + anneeActuelle);
        for(int i=0; i<5; i++){
            annees.add( String.valueOf( anneeActuelle - i ) );
            Log.i(logTag, "Année n°" + i + " : " + String.valueOf( anneeActuelle - i ));
        }

        ArrayAdapter<String> anneeArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, annees);
        anneeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerAnnee.setAdapter(anneeArrayAdapter);

        this.spinnerAnnee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                anneeCourante = annees.get(position);
                Log.i(logTag, "Annee sélectionnée : " + anneeCourante);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void validerMoisEtAnnee(View view){
        Intent intent = new Intent(this, ListeRvActivity.class);
        Bundle paquet = new Bundle();
        paquet.putString("mois", this.spinnerMois.getSelectedItem().toString());
        paquet.putString("annee", this.spinnerAnnee.getSelectedItem().toString());
        paquet.putString("numMois", this.numMoisCourant);
        intent.putExtras(paquet);
        startActivity(intent);
    }

}