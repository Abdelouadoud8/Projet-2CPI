package com.example.projet_2cpi;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class aboutus extends AppCompatActivity{
    TextView AboutUs,WhatWeDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);

        AboutUs = (TextView) findViewById(R.id.titre);
        WhatWeDo = (TextView) findViewById(R.id.whatwedo);
        /********************************About us button********************************/
        TextPaint paint = AboutUs.getPaint();
        float width = paint.measureText("ABOUT US");

        Shader textShader = new LinearGradient(0, 0, width, AboutUs.getTextSize(),
                new int[]{
                        Color.parseColor("#B105F0"),
                        Color.parseColor("#E14594"),
                }, null, Shader.TileMode.CLAMP);
        AboutUs.getPaint().setShader(textShader);

        /********************************About us button********************************/

        /********************************What we do********************************/
        TextPaint paint1 = WhatWeDo.getPaint();
        float width1 = paint1.measureText("ABOUT US");

        Shader textShader1 = new LinearGradient(0, 0, width1, WhatWeDo.getTextSize(),
                new int[]{
                        Color.parseColor("#B105F0"),
                        Color.parseColor("#E14594"),
                }, null, Shader.TileMode.CLAMP);
        WhatWeDo.getPaint().setShader(textShader1);
        /********************************What we do********************************/

    }

    public void FacebookClique(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=guiddini/"));
        startActivity(browserIntent);
    }

    public void InstagramClique(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/guiddini/"));
        startActivity(browserIntent);
    }

    public void GoogleClique(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:guiddini@contact.dz"));
        startActivity(browserIntent);
    }

    public void PhoneClique(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:+213549260454"));
        startActivity(browserIntent);
    }
}
