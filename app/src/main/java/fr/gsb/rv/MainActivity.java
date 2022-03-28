package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.modeles.ModeleGsb;
import fr.gsb.rv.technique.Session;

public class MainActivity extends AppCompatActivity {

    String logTag;

    EditText etMatricule;
    EditText etMdp;
    Button btnValider;
    Button btnAnnuler;
    CheckBox cbLogin;
    ImageView btnAfficherMdp;

    Visiteur visiteur;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.logTag = "GSB_MAIN_ACTIVITY";
        //Log.i("GSB_MAIN_ACTIVITY", "onCreate() : Création de l'activité principale.");

        this.etMatricule = findViewById(R.id.etMatricule);
        this.etMdp = findViewById(R.id.etMdp);
        this.btnValider = findViewById(R.id.btnValider);
        this.btnAnnuler = findViewById(R.id.btnAnnuler);
        this.cbLogin = findViewById(R.id.cbLogin);
        this.btnAfficherMdp = findViewById(R.id.btnAfficherMdp);

        this.btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annuler(v);
            }
        });

        this.btnAfficherMdp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    etMdp.setTransformationMethod(null);
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    etMdp.setTransformationMethod(new PasswordTransformationMethod());
                    return true;
                }
                return false;

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences spGet=this.getSharedPreferences("Login", MODE_PRIVATE);
        etMatricule.setText(spGet.getString("matricule", null));
        etMdp.setText(spGet.getString("mdp", null));

        btnValider.setEnabled(true);
        btnAnnuler.setEnabled(true);

        if(spGet.contains("matricule") && spGet.contains("mdp")){
            cbLogin.setChecked(true);
        }
    }

    public void seConnecter(View view) throws UnsupportedEncodingException {

        String id = this.etMatricule.getText().toString();
        String mdp = this.etMdp.getText().toString();
        //Log.i("GSB_MAIN_ACTIVITY", id);
        //Log.i("GSB_MAIN_ACTIVITY", mdp);

        String matricule = URLEncoder.encode(id, "UTF-8");
        String url = String.format("http://172.20.50.19:5000/visiteurs/%s/%s", id, mdp);


        Response.Listener<JSONObject> ecouteurReponse = new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response){

                Log.i(logTag, "Réponse HTTP : " + response);

                try {
                    MainActivity.this.visiteur = new Visiteur();
                    MainActivity.this.visiteur.setMatricule( response.getString("vis_matricule") );
                    MainActivity.this.visiteur.setNom( response.getString("vis_nom") );
                    MainActivity.this.visiteur.setPrenom( response.getString("vis_prenom") );
                    MainActivity.this.visiteur.setMdp( mdp );
                    Log.i(logTag, "Objet visiteur :" + visiteur.toString());
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i(logTag, "Objet visiteur :" + visiteur.toString());


                if( MainActivity.this.visiteur.getMatricule() != null ){
                    Session.ouvrir(MainActivity.this.visiteur);
                    Session session = Session.getSession();
                    //Log.i("GSB_MAIN_ACTIVITY", this.visiteur.toString());

                    if(cbLogin.isChecked()){
                        SharedPreferences spSet=getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor Ed=spSet.edit();
                        Ed.putString("matricule", MainActivity.this.visiteur.getMatricule());
                        Ed.putString("mdp", MainActivity.this.visiteur.getMdp());
                        Ed.commit();
                    } else{
                        if( !MainActivity.this.cbLogin.isChecked() ){
                            SharedPreferences spSet=getSharedPreferences("Login", MODE_PRIVATE);
                            SharedPreferences.Editor Ed=spSet.edit();
                            Ed.remove("matricule")
                                    .remove("mdp");
                            Ed.commit();
                        }
                    }

                    MainActivity.this.btnValider.setEnabled(false);
                    MainActivity.this.btnAnnuler.setEnabled(false);
                    Intent intent = new Intent(MainActivity.this, MenuRVActivity.class);
                    startActivity(intent);
                }

            }
        } ;

        Response.ErrorListener ecouteurErreur = new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.e(logTag, "Erreur HTTP : " + error.toString());
                MainActivity.this.annuler(view);
                Toast.makeText(MainActivity.this, "Echec à la connexion. Réessayez...", Toast.LENGTH_LONG).show();
                //Log.i("GSB_MAIN_ACTIVITY", "seConnecter() : Connexion Nok.");
            }
        };

        JsonObjectRequest requete = new JsonObjectRequest(Request.Method.GET, url, null, ecouteurReponse, ecouteurErreur);
        RequestQueue fileRequetes = Volley.newRequestQueue(this);
        fileRequetes.add(requete);

    }

    public void annuler(View view){
        this.etMatricule.setText("");
        this.etMdp.setText("");
        //Log.i("GSB_MAIN_ACTIVITY", "Réinitialisation des champs.");
    }

    public void checkCbLogin(View view){
        this.cbLogin.setChecked( !this.cbLogin.isChecked() );
    }

}