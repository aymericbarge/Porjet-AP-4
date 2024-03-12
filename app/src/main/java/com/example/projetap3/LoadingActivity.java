package com.example.projetap3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_view);

        //redirection vers la page "login_view" au bout de 3 secondes
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                //Démarrer la page
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        new Handler().postDelayed(runnable, 3500);
    }
}