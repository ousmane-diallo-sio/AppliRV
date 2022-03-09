package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.gsb.rv.entites.Visiteur;
import fr.gsb.rv.modeles.ModeleGsb;
import fr.gsb.rv.technique.Session;

public class MainActivity extends AppCompatActivity {

    TextView tvErreur;
    EditText etMatricule;
    EditText etMdp;
    Button btnValider;
    Button btnAnnuler;

    Visiteur visiteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("GSB_MAIN_ACTIVITY", "onCreate() : Création de l'activité principale.");


        this.tvErreur = findViewById(R.id.tvErreur);
        this.etMatricule = findViewById(R.id.etMatricule);
        this.etMdp = findViewById(R.id.etMdp);
        this.btnValider = findViewById(R.id.btnValider);
        this.btnAnnuler = findViewById(R.id.btnAnnuler);

        this.btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annuler(v);
            }
        });
    }


    public boolean seConnecter(View view){
        String id = this.etMatricule.getText().toString();
        String mdp = this.etMdp.getText().toString();
        Log.i("GSB_MAIN_ACTIVITY", id);
        Log.i("GSB_MAIN_ACTIVITY", mdp);

        ModeleGsb modeleGsb = ModeleGsb.getInstance();
        this.visiteur = modeleGsb.seConnecter(id, mdp);

        if( this.visiteur != null ){
            Session.ouvrir(this.visiteur);
            Session session = Session.getSession();
            Log.i("GSB_MAIN_ACTIVITY", this.visiteur.toString());

            this.btnValider.setEnabled(false);
            this.btnAnnuler.setEnabled(false);
            Toast.makeText(this, "Connexion réussie", Toast.LENGTH_LONG).show();
            Log.i("GSB_MAIN_ACTIVITY", "seConnecter() : Connexion Ok (null null).");
            Intent intent = new Intent(MainActivity.this, MenuRVActivity.class);
            startActivity(intent);
            return true;
        }
        else {
            this.annuler(view);
            this.tvErreur.setVisibility(View.VISIBLE);
            this.tvErreur.setText("Echec à la connexion. Recommencez...");
            this.tvErreur.setTextColor(ContextCompat.getColor(this, R.color.rouge));
            Log.i("GSB_MAIN_ACTIVITY", "seConnecter() : Connexion Nok.");
            return false;
        }
    }

    public void annuler(View view){
        this.etMatricule.setText("");
        this.etMdp.setText("");
        Log.i("GSB_MAIN_ACTIVITY", "Réinitialisation des champs.");
    }



}