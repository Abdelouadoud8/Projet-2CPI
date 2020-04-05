package com.example.projet_2cpi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_2cpi.profile;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class place extends AppCompatActivity {
    TextView Cycle, Username;
    ImageView Description, Image_btn;
    ImageButton Go_profile_btn,Email_btn, Phone_btn, Linkedin_btn, Fax_btn;;
    DatabaseReference reff;
    String Child, make_child;
    String PhoneNumber, FaxNumber, AdrEmail, AdrLinkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity);

        Child = RecyclerViewAdapter.getUSERNAME();
        //Text Views
        Cycle = (TextView)findViewById(R.id.cycle);
        Username = (TextView)findViewById(R.id.username2);
        //Image Views
        Description = (ImageView)findViewById(R.id.descripton);
        Image_btn = (ImageView)findViewById(R.id.image_btn);
        //ImageButtons
        Go_profile_btn = (ImageButton) findViewById(R.id.go_profile_btn);
        Email_btn = (ImageButton) findViewById(R.id.email_btn2);
        Phone_btn = (ImageButton) findViewById(R.id.phone_btn2);
        Linkedin_btn = (ImageButton) findViewById(R.id.linkedin_btn2);
        Fax_btn = (ImageButton) findViewById(R.id.fax_btn2);


        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cycle = dataSnapshot.child(Child).child("cycle").getValue().toString();
                String employe = dataSnapshot.child(Child).child("employe").getValue().toString();
                String username = dataSnapshot.child(employe).child("name").getValue().toString();
                make_child = username;
                PhoneNumber = dataSnapshot.child(employe).child("Contact").child("phone").getValue().toString();
                FaxNumber = dataSnapshot.child(employe).child("Contact").child("fax").getValue().toString();
                AdrEmail = dataSnapshot.child(employe).child("Contact").child("Email").getValue().toString();
                AdrLinkedin = dataSnapshot.child(employe).child("Contact").child("linkedin").getValue().toString();

                Cycle.setText(cycle);
                Username.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Go_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setChild(make_child);
                RecyclerViewAdapter.setUSERNAME(make_child);
                openProfilActivity2();
            }
        });

        //Social media buttons
        Email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(place.this,"Email : " + AdrEmail,Toast.LENGTH_LONG).show();
            }
        });

        Phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(place.this,"Phone : " + PhoneNumber,Toast.LENGTH_LONG).show();
            }
        });

        Fax_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(place.this,"Fax : " + FaxNumber,Toast.LENGTH_LONG).show();
            }
        });

    }

    public void openProfilActivity2(){
        Intent intent = new Intent(this,profile.class);
        startActivity(intent);


    }

    public void linkedinClique(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AdrLinkedin));
        startActivity(browserIntent);
    }
}
