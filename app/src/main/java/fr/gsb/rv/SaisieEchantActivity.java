package fr.gsb.rv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import fr.gsb.rv.entites.Medicament;
import fr.gsb.rv.entites.RapportVisite;

public class SaisieEchantActivity extends AppCompatActivity {

    String logTag;

    ListView lvMedicaments;
    ArrayList<Medicament> listeMedicaments = new ArrayList<>();

    Integer[] nbEchantillons = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    ArrayList<String> listeMedicamentsOfferts = new ArrayList<>();
    ArrayList<Integer> listeNbMedicamentsOfferts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_echant);

        this.logTag = "GSB_SAISIE_ECHANT_ACTIVITY";

        lvMedicaments = findViewById(R.id.lvMedicaments);

        this.getMedicaments();
    }


    class ItemMedicament extends ArrayAdapter<Medicament> {


        ItemMedicament(){
            super(
                    SaisieEchantActivity.this,
                    R.layout.item_medicament,
                    R.id.tvNomMedicament,
                    listeMedicaments
            );
        }

        @SuppressLint("ResourceAsColor")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View vItem = super.getView(position, convertView, parent);
            View ligneItem = convertView;

            if(ligneItem == null){
                LayoutInflater convertisseur = getLayoutInflater();
                ligneItem = convertisseur.inflate(R.layout.item_medicament, parent, false);
            }

            TextView tvNomMedicament = ligneItem.findViewById(R.id.tvNomMedicament);
            TextView tvDepotLegalMedicament = ligneItem.findViewById(R.id.tvDepotLegalMedicament);
            Spinner spQuantite = ligneItem.findViewById(R.id.spQuantite);

            ArrayAdapter<Integer> spQuantiteAdapter = new ArrayAdapter<Integer>(SaisieEchantActivity.this, android.R.layout.simple_spinner_item, nbEchantillons);

            spQuantiteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spQuantite.setAdapter(spQuantiteAdapter);

            spQuantite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(logTag, "Nb Echantillons : " + spQuantite.getSelectedItem());
                    Log.i(logTag, "Depot légal du Medicament correspondant : " + tvDepotLegalMedicament.getText().toString());

                    //Si le médicament à déja été ajouter à la liste
                    if( listeMedicamentsOfferts.indexOf(tvDepotLegalMedicament.getText().toString()) >= 0 ){
                        int index = listeMedicamentsOfferts.indexOf(tvDepotLegalMedicament.getText().toString());
                        listeNbMedicamentsOfferts.set(index, (Integer) spQuantite.getSelectedItem());
                    } else{
                        listeMedicamentsOfferts.add(tvDepotLegalMedicament.getText().toString());
                        listeNbMedicamentsOfferts.add((Integer) spQuantite.getSelectedItem());
                    }
                    Log.i(logTag, listeMedicamentsOfferts.toString());
                    Log.i(logTag, listeNbMedicamentsOfferts.toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            try {

                int bgColor = position % 2 == 0 ? R.color.grisClair : R.color.white;

                ligneItem.setBackgroundResource(bgColor);
                tvNomMedicament.setText(listeMedicaments.get(position).getMed_nomcommercial());
                tvDepotLegalMedicament.setText(listeMedicaments.get(position).getMed_depotlegal());

            } catch (Exception e){
                Log.e(logTag, e.getMessage());
            }

            return ligneItem;
        }
    }


    public ArrayList<Medicament> getMedicaments(){
        String url = "http://192.168.1.161:5000/medicaments";

        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject reponse = response.getJSONObject(i);

                        Medicament medicament = new Medicament();
                        medicament.setMed_depotlegal(reponse.getString("med_depotlegal"));
                        medicament.setMed_nomcommercial(reponse.getString("med_nomcommercial"));

                        SaisieEchantActivity.this.listeMedicaments.add(medicament);

                        Log.i(logTag, medicament.toString());
                    } catch (JSONException e){
                        Log.e(logTag, e.getMessage());
                    }
                }

                SaisieEchantActivity.ItemMedicament adapter = new SaisieEchantActivity.ItemMedicament();

                lvMedicaments.setAdapter(adapter);
                lvMedicaments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i(logTag, "clique détecté");

                    }
                });

            }
        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(logTag, "Erreur HTTP : " + error.getMessage());
            }
        };

        Request<JSONArray> requete = new JsonArrayRequest(Request.Method.GET, url, null, ecouteurReponse, ecouteurErreur);
        RequestQueue fileRequete = Volley.newRequestQueue(SaisieEchantActivity.this);
        fileRequete.add(requete);

        return listeMedicaments;
    }


    public void validerNbEchantillons(View view){

        try {
            ArrayList<String> listeFinaleMedicament = new ArrayList<>();
            ArrayList<Integer> listeFinaleNbOfferts = new ArrayList<>();

            for(int i=0; i<listeNbMedicamentsOfferts.size(); i++){
                if(listeNbMedicamentsOfferts.get(i) != 0){
                    listeFinaleMedicament.add(listeMedicamentsOfferts.get(i));
                    listeFinaleNbOfferts.add(listeNbMedicamentsOfferts.get(i));

                }
            }

            Log.i(logTag, listeFinaleMedicament.toString());
            Log.i(logTag, listeFinaleNbOfferts.toString());


            Intent intent = new Intent(SaisieEchantActivity.this, SaisieRvActivity.class);

            intent.putExtra("medicamentsOfferts", listeFinaleMedicament)
                .putExtra("nbMedicamentsOfferts", listeFinaleNbOfferts);

            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception e){
            Log.e(logTag, "Erreur lors de la validation : " + e.getMessage());
        }

    }

    public void annulerNbEchantillons(View view){
        Intent intent = new Intent(SaisieEchantActivity.this, SaisieRvActivity.class);
        startActivity(intent);
    }


}