package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

import fr.gsb.rv.entites.Praticien;

public class SaisieRvActivity extends AppCompatActivity {

    String logTag;

    TextView tvDateVisite;
    DatePickerDialog.OnDateSetListener ecouteurDate;
    Spinner spPraticen;
    Spinner spMotif;
    Spinner spCoefConfiance;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_rv);
        logTag = "GSB_SAISIE_ACIVITY";

        tvDateVisite = findViewById(R.id.tvDateVisite);
        spPraticen = findViewById(R.id.spPraticien);
        spMotif = findViewById(R.id.spMotif);
        spCoefConfiance = findViewById(R.id.spCoefConfiance);

        ecouteurDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateSelect = String.format(
                        "%02d/%02d/%04d",
                        dayOfMonth,
                        month +1,
                        year
                );
                tvDateVisite.setText( tvDateVisite.getText() + dateSelect );
            }
        };
    }


    @SuppressLint("NewApi")
    public void selectDateVisite(View view){
        LocalDate ajd = LocalDate.now();

        int jour = ajd.getDayOfMonth();
        int mois = ajd.getMonthValue();
        int annee = ajd.getYear();

        new DatePickerDialog(this, ecouteurDate, annee, mois, jour).show();

    }

    public Praticien getPraticiens(){
        ArrayList<Praticien> listePraticiens = new ArrayList<Praticien>();
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
                                reponse.getString("pra_adresse"),
                                reponse.getString("pra_cp"),
                                reponse.getString("pra_ville"),
                                reponse.getDouble("pra_coefnotoriete"),
                                reponse.getString("typ_code")
                        );

                        listePraticiens.add(praticien);
                        Log.i(logTag, praticien.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        return null;
    }

    public void validerSaisie(View view){
        Toast.makeText(this, "Valider", Toast.LENGTH_LONG).show();
    }

    public void annuler(View view){
        Intent intent = new Intent(SaisieRvActivity.this, MenuRVActivity.class);
        startActivity(intent);
    }


}