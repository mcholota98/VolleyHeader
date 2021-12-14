package com.example.volleyheader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    
    public static final String url="https://api-uat.kushkipagos.com/transfer/v1/bankList";
    private EditText multitxtResultado;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        multitxtResultado=findViewById(R.id.txtmulti);
        mQueue= Volley.newRequestQueue(this);
        jsonParse();

    }

    private void jsonParse(){
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject banco = new JSONObject(response.get(i).toString());

                                String name = banco.getString("code");
                                String surname = banco.getString("name");
                                multitxtResultado.append("Código: "+name +"\n"+"Nombre: "+surname+"\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers= new HashMap();
                headers.put("Content-Type","application/json");
                headers.put("Public-Merchant-Id","84e1d0de1fbf437e9779fd6a52a9ca18");
                return headers;
            }
        };
        mQueue.add(request);
    }
}