package com.example.projetap3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends ArticleActivity {

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialiser l'article
        article = new Article("Nom de l'article", 10.0, R.drawable.image_article);

        // Remplir les vues avec les informations de l'article
        TextView nomArticle = findViewById(R.id.nom_article);
        nomArticle.setText(article.getNom());

        TextView prixArticle = findViewById(R.id.prix_article);
        prixArticle.setText(String.format("%.2f €", article.getPrix()));

        ImageView imageArticle = findViewById(R.id.image_article);
        imageArticle.setImageResource(article.getImage());

        // Bouton "Ajouter au panier"
        Button ajouterPanier = findViewById(R.id.ajouter_panier);
        ajouterPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer la quantité saisie
                int quantite = Integer.parseInt(((EditText) findViewById(R.id.quantite)).getText().toString());

                // Ajouter l'article au panier
                Panier.getInstance().ajouterArticle(article, quantite);

                // Afficher un message de confirmation
                Toast.makeText(MainActivity.this, "Article ajouté au panier", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
