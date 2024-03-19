package com.example.projetap3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class RecapitulatifOrderActivity extends AppCompatActivity {

    private static final String API_URL = "http://192.168.1.15/api";
    private TextView nomArticle;
    private TextView prixArticle;
    private TextView quantiteArticle;
    private TextView totalCommande;
    private Spinner spinnerQuantite;
    private Button boutonConfirmation;

    @SuppressLint("MissingInflatedId")
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

        // Initialiser le spinner
        spinnerQuantite = findViewById(R.id.spinner_quantite);

        // Créer une liste d'entiers
        List<Integer> quantites = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Créer un ArrayAdapter pour les options de quantité
        ArrayAdapter<Integer> adapterQuantite = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, quantites);

        // Définir l'adapter sur le spinner
        spinnerQuantite.setAdapter(adapterQuantite);

        // Gérer l'événement onItemSelected pour réagir à la sélection d'une quantité


        // Afficher la quantité sélectionnée
        String quantiteSelectionnee = null;
        Toast.makeText(RecapitulatifOrderActivity.this, "Quantité sélectionnée : " + quantiteSelectionnee, Toast.LENGTH_SHORT).show();


        // Bouton de confirmation
        boutonConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Envoyer la commande au serveur
                // ...

                // Afficher un message de confirmation
                Toast.makeText(RecapitulatifOrderActivity.this, "Commande confirmée, merci de votre achat!", Toast.LENGTH_SHORT).show();

                // Terminer l'activité
                finish();


            }
        });
    }
}