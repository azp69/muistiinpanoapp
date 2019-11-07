package com.example.muistiinpanot;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

public class Muistiinpano {

    private String id;
    private String omistaja;
    private String otsikko;
    private String data;
    private String luontipaiva;
    private String token;
    private TextViewE textView;

    public Muistiinpano()
    {

    }

    public Muistiinpano(String id, String omistaja, String otsikko, String data, String luontipaiva, String token)
    {
        this.id = id;
        this.omistaja = omistaja;
        this.otsikko = otsikko;
        this.data = data;
        this.luontipaiva = luontipaiva;
        this.token = token;
    }

    public String getId() { return this.id; }
    public String getOmistaja() { return this.omistaja; }
    public String getOtsikko() { return this.otsikko; }
    public String getData() { return this.data; }
    public String getLuontipaiva() { return this.luontipaiva; }
    public String getToken() { return this.token; }
    public TextViewE getTextView() { return this.textView; }

    public void setId(String id) { this.id = id; }
    public void setOmistaja(String omistaja) { this.omistaja = omistaja; }
    public void setOtsikko(String otsikko) { this.otsikko = otsikko; }
    public void setData(String data) { this.data = data; }
    public void setLuontipaiva(String luontipaiva) { this.luontipaiva = luontipaiva; }
    public void setToken(String token) { this.token = token; }
    public void setTextView(TextViewE textView) { this.textView = textView; }

    public void paivitaTextView()
    {
        String teksti = this.getOtsikko() + "\n" + this.getData();
        SpannableStringBuilder str = new SpannableStringBuilder(teksti);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, this.getOtsikko().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        this.textView.setText(str);
        // tv.setText(str);

        // this.textView.setText(Html.fromHtml("<b>" + this.getOtsikko() + "</b><br />\n" + this.getData()));
    }

}

