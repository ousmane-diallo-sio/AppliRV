package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class VisuRvActivity extends AppCompatActivity {

    TextView tvDonnees;

    int rap_num;
    String rap_bilan;
    String pra_cp;
    String pra_nom;
    String rap_date_visite;
    String pra_prenom;
    String pra_ville;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_rv);

        Bundle paquet = this.getIntent().getExtras();
        rap_num = paquet.getInt("rap_num");
        rap_bilan = paquet.getString("rap_bilan");
        pra_cp = paquet.getString("pra_cp");
        pra_nom = paquet.getString("pra_nom");
        rap_date_visite = paquet.getString("rap_date_visite");
        pra_prenom = paquet.getString("pra_prenom");
        pra_ville = paquet.getString("pra_ville");

        tvDonnees = findViewById(R.id.tvDonnees);
        tvDonnees.setText(
                rap_num
                + rap_bilan
                +pra_cp
                +pra_nom
                +rap_date_visite
                +pra_prenom
                +pra_ville
        );

    }
}