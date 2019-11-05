package com.example.muistiinpanot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
class TextViewE extends TextView {
    private Muistiinpano m;

    public TextViewE(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public TextViewE(Context context) {
        super(context);
    }

    public void setMuistiinpano(Muistiinpano m)
    {
        this.m = m;
    }

    public Muistiinpano getMuistiinpano()
    {
        return this.m;
    }
}
