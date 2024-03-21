package com.example.projetap3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    private static final String API_URL = "http://192.168.1.15/api";
    private final List<String> itemList = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_view);

        RecyclerView recyclerView = findViewById(R.id.liste_article);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.article_view, parent, false)) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((TextView) holder.itemView.findViewById(R.id.liste_article)).setText(itemList.get(position));
            }

            @Override
            public int getItemCount() {
                return itemList.size();
            }
        };
        recyclerView.setAdapter(adapter);

        fetchData();
    }

    private void fetchData() {
        StringRequest request = new StringRequest(Request.Method.GET, API_URL + "/articles",
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            itemList.add(jsonObject.getString("name"));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ArticleActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(ArticleActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}