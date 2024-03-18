package com.example.projetap3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    private static final String API_URL = "http://192.168.1.15/api";
    private TextView nomArticle;
    private TextView prixArticle;
    private TextView quantiteArticle;
    private TextView totalCommande;
    private Button boutonConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);

        // Récupérer les informations de l'intent
        Intent intent = getIntent();
        String nom = intent.getStringExtra("nomArticle");
        double prix = intent.getDoubleExtra("prixArticle", 0.0);
        int quantite = intent.getIntExtra("quantiteArticle", 1);

        // Initialiser les vues
        nomArticle = findViewById(R.id.nomArticle);
        prixArticle = findViewById(R.id.prixArticle);
        quantiteArticle = findViewById(R.id.quantiteArticle);
        totalCommande = findViewById(R.id.totalCommande);
        boutonConfirmation = findViewById(R.id.boutonConfirmation);

        // Remplir les vues avec les informations de la commande
        nomArticle.setText(nom);
        prixArticle.setText(String.format("%.2f €", prix));
        quantiteArticle.setText(String.valueOf(quantite));
        totalCommande.setText(String.format("%.2f €", prix * quantite));

        // Bouton de confirmation
        boutonConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envoyer la commande au serveur
                // ...

                // Afficher un message de confirmation
                Toast.makeText(OrderActivity.this, "Commande confirmée, merci de votre achat!", Toast.LENGTH_SHORT).show();

                // Terminer l'activité
                finish();
            }
        });
    }
}
