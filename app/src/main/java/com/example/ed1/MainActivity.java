package com.example.ed1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView can1Level;
    TextView can2Level;
    Button refresh;
    public static RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = findViewById(R.id.button);
        can1Level = findViewById(R.id.can1Level);
        can2Level = findViewById(R.id.can2Level);
        rQueue = Volley.newRequestQueue(this);
    }

    public void refreshData(View v){
        String url = "https://lamp.cse.fau.edu/~dbenne11/ED1miniproj/cans.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Parse the response into JSON
                        try {
                            JSONObject reader = new JSONObject(response);
                            handleResponse(reader);
                        } catch (JSONException e) {
                            can1Level.setText("ERR2");
                            can2Level.setText("ERR2");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                can1Level.setText("ERR");
                can2Level.setText("ERR");
            }
        });

        rQueue.add(stringRequest);
    }

    public void handleResponse(JSONObject data) throws JSONException {
        String level1 = data.getString("can1");
        String level2 = data.getString("can2");
        level1 = level1 + "%";
        level2 = level2 + "%";
        can1Level.setText(level1);
        can2Level.setText(level2);



    }
}