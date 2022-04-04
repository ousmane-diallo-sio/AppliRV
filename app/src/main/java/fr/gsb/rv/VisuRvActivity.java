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
    int rap_coef_confiance;

    TextView tvRapNum;
    TextView tvRapBilan;
    TextView tvPraCp;
    TextView tvPraNom;
    TextView tvRapDateVisite;
    TextView tvPraPrenom;
    TextView tvPraVille;
    TextView tvCoefConfiance;

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
        rap_coef_confiance = paquet.getInt("rap_coef_confiance");

        tvRapNum = findViewById(R.id.tvRapNum);
        tvRapBilan = findViewById(R.id.tvRapBilan);
        tvPraCp = findViewById(R.id.tvPraCp);
        tvPraNom = findViewById(R.id.tvPraNom);
        tvRapDateVisite = findViewById(R.id.tvRapDateVisite);
        tvPraPrenom = findViewById(R.id.tvPraPrenom);
        tvPraVille = findViewById(R.id.tvPraVille);
        tvCoefConfiance = findViewById(R.id.tvCoefConfiance);


        tvRapNum.setText(tvRapNum.getText() + Integer.toString(rap_num));
        tvRapBilan.setText(tvRapBilan.getText() + rap_bilan);
        tvPraCp.setText(tvPraCp.getText() + pra_cp);
        tvPraNom.setText(tvPraNom.getText() + pra_nom);
        tvRapDateVisite.setText(tvRapDateVisite.getText() + rap_date_visite);
        tvPraPrenom.setText(tvPraPrenom.getText() + pra_prenom);
        tvPraVille.setText(tvPraVille.getText() + pra_ville);
        tvCoefConfiance.setText(tvCoefConfiance.getText() + Integer.toString(rap_coef_confiance));

    }
}