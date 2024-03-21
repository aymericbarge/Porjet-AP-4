package com.example.projetap3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String API_URL = "http://192.168.107.9:8000/api";

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        emailEditText = findViewById(R.id.email_placeholder);
        passwordEditText = findViewById(R.id.placeholder_password);
        Button connectButton = findViewById(R.id.button_connexion);
        Button testApiButton = findViewById(R.id.test_api_connection);
        Button inscriptionButton = findViewById(R.id.button_inscription);


        inscriptionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Merci de remplir tous les champs.", Toast.LENGTH_SHORT).show();
                } else {
                    connect(email, password);
                }
            }
        });

        testApiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                testApiConnection();
            }
        });
    }

    private void connect(String email, String password) {
        StringRequest request = new StringRequest(Request.Method.POST, API_URL + "/utilisateurs",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Assuming the response is a JSON object that contains a "token" field
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String token = jsonResponse.getString("token");

                            // Store the token in SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Connecté !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ArticleActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Erreur lors de l'authentification. Veuillez vérifier les informations.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }






    private void testApiConnection() {
        StringRequest request = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivity.this, "API connection successful", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "API connection failed. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Volley.newRequestQueue(this).add(request);
    }
}