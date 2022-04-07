package fr.gsb.rv;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.gsb.rv.entites.Praticien;
import fr.gsb.rv.technique.Session;

public class SaisieRvActivity extends AppCompatActivity {

    String logTag;

    TextView tvDateVisite;
    DatePickerDialog.OnDateSetListener ecouteurDate;
    Spinner spPraticen;
    Spinner spMotif;
    Spinner spCoefConfiance;
    EditText etBilan;

    String dateSelect;
    int praticienSelect;

    ArrayList<String> listeMedicamentsOfferts = new ArrayList<>();
    ArrayList<Integer> listeNbMedicamentsOfferts = new ArrayList<>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_rv);
        this.logTag = "GSB_SAISIE_ACTIVITY";

        tvDateVisite = findViewById(R.id.tvDateVisite);
        spPraticen = findViewById(R.id.spPraticien);
        spMotif = findViewById(R.id.spMotif);
        spCoefConfiance = findViewById(R.id.spCoefConfiance);
        etBilan = findViewById(R.id.etBilan);

        ecouteurDate = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateSelect = String.format(
                        "%02d/%02d/%04d",
                        dayOfMonth,
                        month +1,
                        year
                );
                tvDateVisite.setText( dateSelect );
            }
        };

        String[] motifs = {"Actualisation", "Nouveauté", "Sollicitation praticien", "Remontage"};

        ArrayAdapter<String> motifArrayAdapter = new ArrayAdapter<String>(SaisieRvActivity.this, android.R.layout.simple_spinner_item, motifs);
        motifArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMotif.setAdapter(motifArrayAdapter);

        spMotif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(logTag, "Clique détécté sur la liste des motifs");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<Integer> coefsConfiance = new ArrayList<Integer>();
        for(int i=1; i<=5; i++){
            coefsConfiance.add(i);
        }

        ArrayAdapter<Integer> coefConfianceArrayAdapter = new ArrayAdapter<Integer>(SaisieRvActivity.this, android.R.layout.simple_spinner_item, coefsConfiance);
        coefConfianceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCoefConfiance.setAdapter(coefConfianceArrayAdapter);
        spCoefConfiance.setSelection(4);

        spMotif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(logTag, "Clique détécté sur la liste des coef confiance");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.getPraticiens();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                listeMedicamentsOfferts = data.getExtras().getStringArrayList("medicamentsOfferts");
                listeNbMedicamentsOfferts = data.getExtras().getIntegerArrayList("nbMedicamentsOfferts");

                Log.i(logTag, "Résultat final med : " + listeMedicamentsOfferts.toString());
                Log.i(logTag, "Résultat final nb med : " + listeNbMedicamentsOfferts.toString());
            }
        }

    }


    @SuppressLint("NewApi")
    public void selectDateVisite(View view){
        LocalDate ajd = LocalDate.now();

        int jour = ajd.getDayOfMonth();
        int mois = ajd.getMonthValue();
        int annee = ajd.getYear();

        new DatePickerDialog(this, ecouteurDate, annee, mois, jour).show();

    }


    public ArrayList<Praticien> getPraticiens(){
        ArrayList<Praticien> listePraticiens = new ArrayList<Praticien>();
        String url = "http://172.20.50.19:5000/praticiens";

        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject reponse = response.getJSONObject(i);

                        Praticien praticien = new Praticien(
                                reponse.getInt("pra_num"),
                                reponse.getString("pra_nom"),
                                reponse.getString("pra_prenom"),
                                reponse.getString("pra_ville")
                        );

                        listePraticiens.add(praticien);
                        //Log.i(logTag, praticien.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ArrayAdapter<Praticien> praticienArrayAdapter = new ArrayAdapter<Praticien>(SaisieRvActivity.this, android.R.layout.simple_spinner_item, listePraticiens);
                praticienArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spPraticen.setAdapter(praticienArrayAdapter);

                spPraticen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Log.i(logTag, "Clique détecté sur l'élement " + position + "de la liste");
                        praticienSelect = listePraticiens.get(position).getPra_num();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SaisieRvActivity.this, "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_LONG).show();
                Log.e(logTag, error.getMessage());
            }
        };

        Request<JSONArray> requete = new JsonArrayRequest(Request.Method.GET, url, null, ecouteurReponse, ecouteurErreur);
        RequestQueue fileRequete = Volley.newRequestQueue(SaisieRvActivity.this);
        fileRequete.add(requete);

        return listePraticiens;
    }

    public void saisirEchantillonsOfferts(View view){
        Intent intent = new Intent(this, SaisieEchantActivity.class);
        startActivityForResult(intent, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void validerSaisie(View view) throws JSONException {

        if( dateSelect != null && spPraticen.getSelectedItem() != null && spMotif.getSelectedItem() != null && etBilan.getText().toString() != "" && spCoefConfiance.getSelectedItem() != null ){
            String url = "http://172.20.50.19:5000/rapports";

            Response.Listener ecouteurReponse = new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    Toast.makeText(SaisieRvActivity.this, "Le rapport à bien été créer", Toast.LENGTH_LONG);
                    Log.i(logTag, "Réponse HTTP : " + response.toString());
                }
            };

            Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SaisieRvActivity.this, "Une erreur est survenue, veuillez réessayer", Toast.LENGTH_SHORT).show();
                    Log.e(logTag, "Erreur HTTP : " + error.getMessage());
                }
            };

            JSONObject objetJSON = new JSONObject();
            objetJSON.put("matricule", Session.getLeVisiteur().getMatricule())
                    .put("praticien", praticienSelect)
                    .put("visite", dateSelect)
                    .put("bilan", etBilan.getText().toString())
                    .put("motif", spMotif.getSelectedItem())
                    .put("coef_confiance", spCoefConfiance.getSelectedItem())
                    .put("date_redaction", LocalDate.now());

            Request<JSONObject> requete = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    objetJSON,
                    ecouteurReponse,
                    ecouteurErreur
            );
            RequestQueue fileRequete = Volley.newRequestQueue(SaisieRvActivity.this);
            fileRequete.add(requete);

            //String urlMedicaments = String.format("http://172.20.50.19:5000/rapports/echantillons/%s%s", Session.getLeVisiteur().getMatricule(), 10 /*num du dernier rapport*/);

        }
        else{
            Toast.makeText(this, "Tous les champs doivent être complétés.", Toast.LENGTH_LONG).show();
        }

    }

    public void annuler(View view){
        Toast.makeText(this, "Annulation... le rapport de visite n'a pas été enregistré.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SaisieRvActivity.this, MenuRVActivity.class);
        startActivity(intent);
    }


}