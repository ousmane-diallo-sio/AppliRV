package fr.gsb.rv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.gsb.rv.technique.Session;

public class MenuRVActivity extends AppCompatActivity {

    TextView tvBienvenue;
    RelativeLayout btnSaisir;
    RelativeLayout btnConsulter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rv);

        tvBienvenue = findViewById(R.id.tvBienvenue);
        btnSaisir = findViewById(R.id.btnSaisir);
        btnConsulter = findViewById(R.id.btnConsulter);

        tvBienvenue.setText( tvBienvenue.getText() + " " + Session.getLeVisiteur().getPrenom() );

        btnSaisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuRVActivity.this, SaisieRvActivity.class);
                startActivity(intent);
            }
        });

        btnConsulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuRVActivity.this, RechercheRvActivity.class);
                startActivity(intent);
            }
        });
    }

    public void seDeconnecter(View view) {
        Session.fermer();
        Intent intent = new Intent(MenuRVActivity.this, MainActivity.class);
        startActivity(intent);
    }
}