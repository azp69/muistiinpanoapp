package com.example.muistiinpanot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        // setContentView(R.layout.activity_main);
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
                        // Display the first 500 characters of the response string.
                        // ParseJSON(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

        // PoistaMuistiinpanoPaikallisesti(m);
    }

    public void PoistaMuistiinpanoPaikallisesti(Muistiinpano m)
    {
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
                        // Display the first 500 characters of the response string.
                        ParseJSON(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void LisaaMuistiinpanoPalvelimelle(final Muistiinpano m){
        final String userToken = "3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0";

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://palikka.org/muistiinpano/testi.php"; // <----enter your post url here
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                Log.i("response: ", response);
                m.setId("1");
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("Error: ", error.toString());
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
                Muistiinpano m = new Muistiinpano("1", "1", "Otsikko", "Data", "1" , "token");
                m.setTextView(t);
                dialog.setMuistiinpano(((TextViewE) v).getMuistiinpano());
                dialog.setTextView(t);

                dialog.show(getSupportFragmentManager(), "");
                // Log.i("juu", "täällä");
            }
        });

        t.setTextSize(20f);

        // t.setText(Html.fromHtml("<b>" + m.getOtsikko() + "</b><br />\n" + m.getData()));
        m.paivitaTextView();

        LinearLayout l = findViewById(R.id.scrollLayout);
        l.addView(t);
        // return t;
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
        // setContentView(R.layout.muistiinpano);
        DialogNaytaMuistiinpano dialog = new DialogNaytaMuistiinpano();
        Muistiinpano m = new Muistiinpano("", "", "Otsikko", "", "" , "");

        dialog.setMuistiinpano(m);

        dialog.show(getSupportFragmentManager(), "");
    }
}
