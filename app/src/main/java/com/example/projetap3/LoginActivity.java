package com.example.projetap3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private static final String API_URL = "http://www3.sio.local/api";

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        emailEditText = findViewById(R.id.placeholder_email);
        passwordEditText = findViewById(R.id.placeholder_password);
        loginButton = findViewById(R.id.button_connexion);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                } else {
                    login(email, password);
                }

            }

        });
    }
    
    private void login(String email, String password) {
        StringRequest request = new StringRequest(Request.Method.POST, API_URL, "login",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String token = jsonObject.getString("email");

                            SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", token);
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, FaudraFaireUneActivitePourCa.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Erreur de connexion. Veuillez vérifier vos informations de connexion", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Vérifiez votre connexion Internet puis réessayez", Toast.LENGTH_SHORT).show();
            }
        };
        request.setTag(new HashMap<String, String>() {{
            put("email", email);
            put("password", password);
        }});

        Volley.getInstance(this).add(request);
    }
}