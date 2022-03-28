package fr.gsb.rv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fr.gsb.rv.entites.RapportVisite;
import fr.gsb.rv.technique.Session;

public class ListeRvActivity extends AppCompatActivity {

    String logTag;

    LinearLayout ltContainer;

    String moisSelectionne;
    String anneeSelectionne;
    String numMoisSelectionne;

    ListView lvRapports;

    List<RapportVisite> listeRapports = new ArrayList<RapportVisite>();


    class ItemRapportVisiteAdapter extends ArrayAdapter<RapportVisite>{

        ItemRapportVisiteAdapter(){
            super(
                    ListeRvActivity.this,
                    R.layout.item_rapport_visite,
                    R.id.tvItemDateVisite,
                    listeRapports
            );
        }

        @SuppressLint("ResourceAsColor")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View vItem = super.getView(position, convertView, parent);

            TextView tvItemNomPraticien = vItem.findViewById(R.id.tvItemNomPraticien);
            TextView tvItemDateVisite = vItem.findViewById(R.id.tvItemDateVisite);

            try {

                int bgColor = position % 2 == 0 ? R.color.grisClair : R.color.white;

                tvItemNomPraticien.setText(listeRapports.get(position).getPra_nom());
                tvItemNomPraticien.setBackgroundResource(bgColor);

                tvItemDateVisite.setText(listeRapports.get(position).getRap_date_visite());
                tvItemDateVisite.setBackgroundResource(bgColor);
            } catch (Exception e){
                Log.e(logTag, e.getMessage());
            }

            return vItem;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);

        this.logTag = "GSB_LISTE_RV_ACTIVITY";

        Bundle paquet = this.getIntent().getExtras();

        moisSelectionne = paquet.getString("mois");
        anneeSelectionne = paquet.getString("annee");
        numMoisSelectionne = paquet.getString("numMois");

        this.ltContainer = findViewById(R.id.ltContainer);
        this.lvRapports = findViewById(R.id.lvRapports);


        this.getRapports();
    }


    public void getRapports(){
        String url = String.format("http://172.20.50.19:5000/rapports/%s/%s/%s", Session.getLeVisiteur().getMatricule(), numMoisSelectionne, anneeSelectionne);
        Log.i(logTag, "Requete HTTP effectuée : " + url);

        Response.Listener<JSONArray> ecouteurReponse = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(logTag, "Données Recup : " + response.toString());

                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject element = response.getJSONObject(i);
                        RapportVisite rapportVisite = new RapportVisite();

                        rapportVisite.setRap_num(Integer.parseInt(element.getString("rap_num")));
                        rapportVisite.setRap_bilan(element.getString("rap_bilan"));
                        rapportVisite.setPra_cp(element.getString("pra_cp"));
                        rapportVisite.setPra_nom(element.getString("pra_nom"));
                        rapportVisite.setRap_date_visite(element.getString("rap_date_visite"));
                        rapportVisite.setPra_prenom(element.getString("pra_prenom"));
                        rapportVisite.setPra_ville(element.getString("pra_ville"));

                        listeRapports.add(rapportVisite);

                        Log.i(logTag, "Chargement d'un nouveau rapport... (" + i + ") \n : " + listeRapports.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(listeRapports.size() != 0){
                    ItemRapportVisiteAdapter adapter = new ItemRapportVisiteAdapter();

                    Collections.reverse(listeRapports);
                    synchronized (listeRapports){
                        listeRapports.notify();
                    }
                    lvRapports.setAdapter(adapter);
                    lvRapports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i(logTag, "clique détecté");
                            Intent intent = new Intent(ListeRvActivity.this, VisuRvActivity.class);
                            Bundle paquet = new Bundle();
                            RapportVisite rapportCourant = listeRapports.get(position);
                            paquet.putInt("rap_num", rapportCourant.getRap_num());
                            paquet.putString("rap_bilan", rapportCourant.getRap_bilan());
                            paquet.putString("pra_cp", rapportCourant.getPra_cp());
                            paquet.putString("pra_nom", rapportCourant.getPra_nom());
                            paquet.putString("rap_date_visite", rapportCourant.getRap_date_visite());
                            paquet.putString("pra_prenom", rapportCourant.getPra_prenom());
                            paquet.putString("pra_ville", rapportCourant.getPra_ville());
                            intent.putExtras(paquet);
                            startActivity(intent);
                        }
                    });
                }
            }

        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(logTag, "Erreur HTTP : " + error.toString());
                TextView txt = new TextView(ListeRvActivity.this);
                txt.setText("Aucun résultat...");
                txt.setGravity(Gravity.CENTER);
                txt.setTextSize(35);
                ltContainer.addView(txt);
            }
        };

        JsonArrayRequest requete = new JsonArrayRequest(Request.Method.GET, url, null, ecouteurReponse, ecouteurErreur);
        RequestQueue fileRequete = Volley.newRequestQueue(this);
        fileRequete.add(requete);
    }


}