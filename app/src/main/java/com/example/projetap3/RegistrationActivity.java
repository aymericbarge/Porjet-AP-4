package com.example.projetap3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private static final String API_URL = "http://192.168.1.15/api";

    private EditText emailEditText;
    private EditText confirmEmailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_view);

        emailEditText = findViewById(R.id.placeholder_email);
        confirmEmailEditText = findViewById(R.id.placeholder_confirm_email);
        passwordEditText = findViewById(R.id.placeholder_mdp);
        confirmPasswordEditText = findViewById(R.id.placeholder_confirm_mdp);
        Button registerButton = findViewById(R.id.button_registration);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                String email = emailEditText.getText().toString();
                String confirmEmail = confirmEmailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (email.isEmpty() || confirmEmail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!email.equals(confirmEmail)) {
                    Toast.makeText(RegistrationActivity.this, "Emails do not match", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    register(email, password);
                }
            }
        });
    }

    private void register(String email, String password) {
        StringRequest request = new StringRequest(Request.Method.POST, API_URL + "register",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegistrationActivity.this, "Registration failed. Please try again", Toast.LENGTH_SHORT).show();
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
}