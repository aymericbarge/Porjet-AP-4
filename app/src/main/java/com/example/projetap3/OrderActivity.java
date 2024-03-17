package com.example.projetap3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CommandeActivity extends AppCompatActivity {

    private Article article;
    private EditText quantite;
    private Spinner variante;
    private RadioGroup optionLivraison;
    private EditText informationPaiement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        // Récupérer l'article à partir de l'intent
        article = getIntent().getParcelableExtra("article");

        // Initialiser les vues
        quantite = (EditText) findViewById(R.id.quantite);
        variante = (Spinner) findViewById(R.id.variante);
        optionLivraison = (RadioGroup) findViewById(R.id.optionLivraison);
        informationPaiement = (EditText) findViewById(R.id.informationPaiement);

        // Remplir les vues avec les informations de l'article
        TextView nomArticle = (TextView) findViewById(R.id.nomArticle);
        nomArticle.setText(article.getNom());

        ImageView imageArticle = (ImageView) findViewById(R.id.imageArticle);
        imageArticle.setImageResource(article.getImage());

        TextView prixArticle = (TextView) findViewById(R.id.prixArticle);
        prixArticle.setText(String.valueOf(article.getPrix()));

        // Remplir la liste des variantes
        List<String> listeVariantes = new ArrayList<>();
        for (String var : article.getVariantes()) {
            listeVariantes.add(var);
        }
        ArrayAdapter<String> adapterVariantes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listeVariantes);
        variante.setAdapter(adapterVariantes);

        // Remplir les options de livraison
        List<String> listeOptionsLivraison = new ArrayList<>();
        for (String option : article.getOptionsLivraison()) {
            listeOptionsLivraison.add(option);
        }
        ArrayAdapter<String> adapterOptionsLivraison = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listeOptionsLivraison);
        optionLivraison.setAdapter(adapterOptionsLivraison);

        // Bouton de commande
        Button boutonCommande = (Button) findViewById(R.id.boutonCommande);
        boutonCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Valider les informations de la commande
                boolean quantiteValide = !quantite.getText().toString().isEmpty();
                boolean varianteValide = variante.getSelectedItemPosition() != 0;
                boolean optionLivraisonValide = optionLivraison.getCheckedRadioButtonId() != -1;
                boolean informationPaiementValide = !informationPaiement.getText().toString().isEmpty();

                if (quantiteValide && varianteValide && optionLivraisonValide && informationPaiementValide) {
                    // Passer la commande
                    Commande commande = new Commande(
                            article.getId(),
                            Integer.parseInt(quantite.getText().toString()),
                            variante.getSelectedItemPosition(),
                            optionLivraison.getCheckedRadioButtonId(),
                            informationPaiement.getText().toString()
                    );

                    // Envoyer la commande au serveur
                    // ...

                    // Afficher un message de confirmation
                    Toast.makeText(CommandeActivity.this, "Commande passée avec succès", Toast.LENGTH_SHORT).show();

                    // Terminer l'activité
                    finish();
                } else {
                    // Afficher un message d'erreur
                    Toast.makeText(CommandeActivity.this, "Veuillez vérifier les informations de votre commande", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}