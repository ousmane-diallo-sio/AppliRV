package fr.gsb.rv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.List;

import fr.gsb.rv.entites.RapportVisite;

public class ListeRvActivity extends AppCompatActivity {

    String logTag;

    TextView tvMois;
    TextView tvAnnee;

    String moisSelectionne;
    String anneeSelectionne;
    String numMoisSelectionne;

    ListView lvRapports;

    List<RapportVisite> listeRapports;

    class ItemRapportVisiteAdapter extends ArrayAdapter<RapportVisite>{

        public ItemRapportVisiteAdapter(){
            super(
                    ListeRvActivity.this,
                    R.layout.item_rapport_visite,
                    R.id.tvItemNumRapport,
                    listeRapports
            );
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View vItem = super.getView(position, convertView, parent);

            TextView tvItemNumRapport = vItem.findViewById(R.id.tvItemNumRapport);
            TextView tvItemNomPraticien = vItem.findViewById(R.id.tvItemNomPraticien);
            TextView tvItemVillePraticien = vItem.findViewById(R.id.tvItemVillePraticien);
            TextView tvItemDateVisite = vItem.findViewById(R.id.tvItemDateVisite);

            tvItemNumRapport.setText(listeRapports.get(position).getRap_num());
            tvItemNomPraticien.setText(listeRapports.get(position).getPra_nom());
            tvItemVillePraticien.setText(listeRapports.get(position).getPra_ville());
            tvItemDateVisite.setText(listeRapports.get(position).getRap_date_visite());

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

        this.tvMois = findViewById(R.id.tvMois);
        this.tvAnnee = findViewById(R.id.tvAnnee);
        this.lvRapports = findViewById(R.id.lvRapports);

        this.tvMois.setText(this.moisSelectionne);
        this.tvAnnee.setText(this.anneeSelectionne);

        this.getRapports();

        ItemRapportVisiteAdapter adapter = new ItemRapportVisiteAdapter();
        lvRapports.setAdapter(adapter);
        lvRapports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(logTag, v.toString());
            }
        });
    }

    public void getRapports(){
        String url = String.format("http://172.20.50.19:5000/rapports/a131/%s/%s", numMoisSelectionne, anneeSelectionne);
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

                        listeRapports = new ArrayList<RapportVisite>();
                        listeRapports.add(rapportVisite);

                        Log.i(logTag, "Chargement d'un nouveau rapport... \n : " + listeRapports.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

        };

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(logTag, "Erreur HTTP : " + error.toString());
            }
        };

        JsonArrayRequest requete = new JsonArrayRequest(Request.Method.GET, url, null, ecouteurReponse, ecouteurErreur);
        RequestQueue fileRequete = Volley.newRequestQueue(this);
        fileRequete.add(requete);
    }


}