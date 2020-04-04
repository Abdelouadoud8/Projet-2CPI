package com.example.projet_2cpi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.projet_2cpi.profile;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class place extends AppCompatActivity {
    TextView Cycle, Username;
    ImageView Description, Image_btn;
    ImageButton Go_profile_btn;
    DatabaseReference reff;
    String Child, make_child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity);

        Child = RecyclerViewAdapter.getUSERNAME();
        //Text Views
        Cycle = (TextView)findViewById(R.id.cycle);
        Username = (TextView)findViewById(R.id.username2);
        //Image Views
        /* Description = (ImageView)findViewById(R.id.descripton);
        Image_btn = (ImageView)findViewById(R.id.image_btn); */
        //Buttons
        Go_profile_btn = (ImageButton) findViewById(R.id.go_profile_btn);

        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cycle = dataSnapshot.child(Child).child("cycle").getValue().toString();
                String employe = dataSnapshot.child(Child).child("employe").getValue().toString();
                String username = dataSnapshot.child(employe).child("name").getValue().toString();
                make_child = username;
                //String poste = dataSnapshot.child("Users").child(Child).child("Poste").getValue().toString();
                /* String picture_link = dataSnapshot.child("image").getValue(String.class);
                Picasso.get().load(picture_link).into(Picture); */

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

    }

    public void openProfilActivity2(){
        Intent intent = new Intent(this,profile.class);
        startActivity(intent);
    }
}
