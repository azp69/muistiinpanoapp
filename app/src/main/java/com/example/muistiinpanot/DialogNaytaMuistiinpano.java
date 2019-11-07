package com.example.muistiinpanot;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogNaytaMuistiinpano extends DialogFragment {

    private Muistiinpano m;
    private TextViewE t;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.muistiinpano, null);

        final TextView otsikkoText = (TextView) dialogView.findViewById(R.id.otsikkoText);
        final TextView dataText = (TextView) dialogView.findViewById(R.id.dataText);

        builder.setPositiveButton("Tallenna", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m.setData(dataText.getText().toString());
                m.setOtsikko(otsikkoText.getText().toString());

                if (t == null) // Uusi muistiinpano
                {
                    MainActivity callingActivity = (MainActivity) getActivity();

                    callingActivity.LuoTextView(m);
                    m.paivitaTextView();
                    callingActivity.LisaaMuistiinpanoPalvelimelle(m);

                }
                else // Vanhan muokkaus
                {
                    Log.i("Joo", "Vanhan muokkaus.");
                    MainActivity callingActivity = (MainActivity) getActivity();
                    callingActivity.PaivitaMuistiinpanoPalvelimelle(m);
                    m.paivitaTextView();
                }

                dismiss();
            }
        });

        builder.setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setNeutralButton("Poista", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity act = (MainActivity) getActivity();
                act.PoistaMuistiinpanoPalvelimelta(m);
                act.PoistaMuistiinpanoPaikallisesti(m);
                dismiss();
            }
        });



        /*
        Button peruutaBtn = (Button) dialogView.findViewById(R.id.peruutaBtn);

        peruutaBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        Button poistaBtn = (Button) dialogView.findViewById(R.id.poistaBtn);

        poistaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity act = (MainActivity) getActivity();

                act.PoistaMuistiinpanoPalvelimelta(m);
                act.PoistaMuistiinpanoPaikallisesti(m);
                dismiss();
            }
        });


        Button tallennaBtn = (Button) dialogView.findViewById(R.id.tallennaBtn);

        tallennaBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                m.setData(dataText.getText().toString());
                m.setOtsikko(otsikkoText.getText().toString());

                if (t == null) // Uusi muistiinpano
                {
                    MainActivity callingActivity = (MainActivity) getActivity();

                    callingActivity.LuoTextView(m);
                    m.paivitaTextView();
                    callingActivity.LisaaMuistiinpanoPalvelimelle(m);

                }
                else // Vanhan muokkaus
                {
                    Log.i("Joo", "Vanhan muokkaus.");
                    MainActivity callingActivity = (MainActivity) getActivity();
                    callingActivity.PaivitaMuistiinpanoPalvelimelle(m);
                    m.paivitaTextView();
                }

                dismiss();
            }
        });


        */

        builder.setView(dialogView).setMessage("Muistiinpano");

        otsikkoText.setText(m.getOtsikko());
        dataText.setText(m.getData());


        return builder.create();
    }

    public void setMuistiinpano(Muistiinpano m)
    {
        this.m = m;
    }
    public void setTextView(TextViewE t) { this.t = t; }

}
