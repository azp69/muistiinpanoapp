package com.example.muistiinpanot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private ArrayList<Muistiinpano> muistiinpanot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        muistiinpanot = new ArrayList<Muistiinpano>();

        setContentView(R.layout.linear_layout);

    }

    public void PoistaMuistiinpanoPalvelimelta(Muistiinpano m)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String userToken = "3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0";
        String muistiinpanoId = m.getId();

        String url ="https://palikka.org/muistiinpano/testi.php?usertoken=" + userToken + "&poista=" + muistiinpanoId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    public void PoistaMuistiinpanoPaikallisesti(Muistiinpano m)
    {
        LinearLayout l = findViewById(R.id.scrollLayout);
        l.removeView(m.getTextView());
        muistiinpanot.remove(m);
    }

    public void HaeDataUrlista()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String userToken = "3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0";
        String url ="https://palikka.org/muistiinpano/testi.php?usertoken=" + userToken;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ParseJSON(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    public void PaivitaMuistiinpanoPalvelimelle(final Muistiinpano m){
        final String userToken = "3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0";
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://palikka.org/muistiinpano/testi.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonData = new JSONObject(response);
                    JSONArray jsonAr = jsonData.getJSONArray("muistiinpanot");
                    for (int i = 0; i < jsonAr.length(); i++)
                    {
                        JSONObject c = jsonAr.getJSONObject(i);
                        m.setId(c.getString("id"));
                        m.setOmistaja(c.getString("omistaja"));
                        m.setLuontipaiva(c.getString("lisatty"));
                        m.setToken(c.getString("token"));
                    }
                }
                catch(Exception e)
                {
                    Log.e("Error: ", e.toString());
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.toString());
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usertoken", userToken);
                params.put("id", m.getId());
                params.put("otsikko", m.getOtsikko().toString());
                params.put("data", m.getData().toString());
                params.put("paivita", "1");
                return params;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    public void LisaaMuistiinpanoPalvelimelle(final Muistiinpano m){
        final String userToken = "3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0";

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://palikka.org/muistiinpano/testi.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonData = new JSONObject(response);
                    JSONArray jsonAr = jsonData.getJSONArray("muistiinpanot");

                    for (int i = 0; i < jsonAr.length(); i++)
                    {
                        JSONObject c = jsonAr.getJSONObject(i);
                        m.setId(c.getString("id"));
                        m.setOmistaja(c.getString("omistaja"));
                        m.setLuontipaiva(c.getString("lisatty"));
                        m.setToken(c.getString("token"));
                    }

                }
                catch(Exception e)
                {
                    Log.e("Error: ", e.toString());
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.toString());
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usertoken", userToken);
                params.put("otsikko", m.getOtsikko().toString());
                params.put("data", m.getData().toString());
                params.put("lisaa", "1");
                return params;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    public void LuoTextView(Muistiinpano m)
    {
        TextViewE t = new TextViewE(this);
        t.setMuistiinpano(m);
        m.setTextView(t);


        t.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextViewE t = (TextViewE) v;

                String viesti = t.getMuistiinpano().getLuontipaiva();

                // Toast.makeText(MainActivity.this, viesti, Toast.LENGTH_LONG).show();

                DialogNaytaMuistiinpano dialog = new DialogNaytaMuistiinpano();
                Muistiinpano m = new Muistiinpano("", "", "", "", "" , "");
                m.setTextView(t);
                dialog.setMuistiinpano(((TextViewE) v).getMuistiinpano());
                dialog.setTextView(t);

                dialog.show(getSupportFragmentManager(), "");
            }
        });

        t.setTextSize(16f);
        t.setTextColor(getResources().getColor(R.color.colorText));
        t.setPadding(0,30,0,30);

        m.paivitaTextView();

        LinearLayout l = findViewById(R.id.scrollLayout);
        l.addView(t);
    }

    public void ParseJSON(String data)
    {
        try {
            JSONObject jsonData = new JSONObject(data);
            JSONArray jsonAr = jsonData.getJSONArray("muistiinpanot");

            for (int i = 0; i < jsonAr.length(); i++)
            {
                JSONObject c = jsonAr.getJSONObject(i);
                Muistiinpano m = new Muistiinpano(c.getString("id"), c.getString("omistaja"), c.getString("otsikko"), c.getString("data"), c.getString("lisatty"), c.getString("token"));
                muistiinpanot.add(m);
                LuoTextView(m);
            }

        }
        catch(Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void haeMuistiinpanotPalvelimelta (View v)
    {
        Toast.makeText(this, "Haetaan muistiinpanoja palvelimelta..", Toast.LENGTH_LONG).show();
        LinearLayout l = findViewById(R.id.scrollLayout);
        l.removeAllViews();
        muistiinpanot.clear();
        HaeDataUrlista();
    }

    public void uusiMuistiinpano(View v)
    {
        DialogNaytaMuistiinpano dialog = new DialogNaytaMuistiinpano();

        Muistiinpano m = new Muistiinpano("", "", "Otsikko", "", "" , "");

        dialog.setMuistiinpano(m);

        dialog.show(getSupportFragmentManager(), "");
    }
}
