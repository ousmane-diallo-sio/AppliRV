package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ListeRvActivity extends AppCompatActivity {

    TextView tvMois;
    TextView tvAnnee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rv);

        Bundle paquet = this.getIntent().getExtras();

        this.tvMois = findViewById(R.id.tvMois);
        this.tvAnnee = findViewById(R.id.tvAnnee);

        this.tvMois.setText(paquet.getString("mois"));
        this.tvAnnee.setText(paquet.getString("annee"));
    }
}