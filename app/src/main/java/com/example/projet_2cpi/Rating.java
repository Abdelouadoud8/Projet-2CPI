package com.example.projet_2cpi;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.RatingBar;

import android.widget.TextView;
import android.widget.Toast;


import java.security.acl.AclNotFoundException;

import static android.view.animation.AnimationUtils.*;

public class Rating extends AppCompatActivity {
    TextView titlerate, resultrate;
    Button btnfeedback;
    ImageView starOne;
    RatingBar rateStars;
    String answerValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_layout);
        titlerate = findViewById(R.id.titlerate);
        resultrate = findViewById(R.id.resultrate);
        btnfeedback = findViewById(R.id.btnfeedback);
        starOne = findViewById(R.id.starone);
        rateStars = findViewById(R.id.rateStars);
        final Animation starAnim, btt;



        //load Animation
        starAnim = loadAnimation(this, R.anim.staranim);
        btt = loadAnimation(this, R.anim.btt);

        //give animate
        starOne.startAnimation(starAnim);
        btnfeedback.startAnimation(btt);
        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                }catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps?hl=fr")));


                }
            }
        });

        //give condition
        rateStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                answerValue = String.valueOf((int) (rateStars.getRating()));
                if (answerValue.equals("0")){
                    starOne.setImageResource(R.drawable.star0);
                    btnfeedback.startAnimation(btt);
                    resultrate.setText("So bad");
                } else if (answerValue.equals("1")) {
                    starOne.setImageResource(R.drawable.star1);
                    starOne.startAnimation(starAnim);
                    starOne.animate().alpha(1).setDuration(300).start();
                    btnfeedback.startAnimation(btt);
                    resultrate.setText("Really bad!");
                } else if (answerValue.equals("2") || answerValue.equals("2")) {
                    starOne.setImageResource(R.drawable.star2);
                    starOne.animate().alpha(1).setDuration(300).start();
                    starOne.startAnimation(starAnim);
                    btnfeedback.startAnimation(btt);
                    resultrate.setText("Not that good!");
                } else if (answerValue.equals("3")) {
                    starOne.setImageResource(R.drawable.star3);
                    starOne.animate().alpha(1).setDuration(300).start();
                    starOne.startAnimation(starAnim);
                    btnfeedback.startAnimation(btt);
                    resultrate.setText("Good Job");
                } else if (answerValue.equals("4")) {
                    starOne.setImageResource(R.drawable.star4);
                    starOne.animate().alpha(1).setDuration(300).start();
                    starOne.startAnimation(starAnim);
                    btnfeedback.startAnimation(btt);
                    resultrate.setText("Awesome!");
                } else if (answerValue.equals("5")) {
                    starOne.setImageResource(R.drawable.star5);
                    starOne.animate().alpha(1).setDuration(300).start();
                    starOne.startAnimation(starAnim);
                    btnfeedback.startAnimation(btt);
                    resultrate.setText("Amazing!");
                } else {
                    Toast.makeText(getApplicationContext(), "No point", Toast.LENGTH_SHORT).show();
                }


            }
        });


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

}

