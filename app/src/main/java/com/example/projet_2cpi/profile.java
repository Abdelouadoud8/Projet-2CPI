package com.example.projet_2cpi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_2cpi.MainActivity;
import com.example.projet_2cpi.profile;
import com.example.projet_2cpi.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity {
    TextView Username, Poste, Matin, Soir, NB;
    ImageView Picture, DayPicture,DayPicture2;
    ImageButton Email_btn, Phone_btn, Linkedin_btn, Fax_btn;
    Button Dimanche_btn, Lundi_btn, Mardi_btn, Mercredi_btn, Jeudi_btn;
    DatabaseReference reff;
    private static String Child;
    String PhoneNumber, FaxNumber, AdrEmail, AdrLinkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        Child = RecyclerViewAdapter.getUSERNAME();
        //Text Views
        Username =(TextView)findViewById(R.id.username);
        Poste =(TextView)findViewById(R.id.poste);
        Matin =(TextView)findViewById(R.id.matin);
        Soir = (TextView)findViewById(R.id.soir);
        NB = (TextView)findViewById(R.id.NB);
        //Image View
        Picture = (ImageView)findViewById(R.id.U_P_picture);
        DayPicture = (ImageView)findViewById(R.id.dayPicture);
        DayPicture2 = (ImageView)findViewById(R.id.dayPicture2);
        //Buttons
        Dimanche_btn = (Button)findViewById(R.id.Dimanche_btn);
        Lundi_btn = (Button)findViewById(R.id.Lundi_btn);
        Mardi_btn = (Button)findViewById(R.id.Mardi_btn);
        Mercredi_btn = (Button)findViewById(R.id.Mercredi_btn);
        Jeudi_btn = (Button)findViewById(R.id.Jeudi_btn);
        //Image Buttons
        Email_btn = (ImageButton) findViewById(R.id.email_btn);
        Phone_btn = (ImageButton) findViewById(R.id.phone_btn);
        Linkedin_btn = (ImageButton) findViewById(R.id.linkedin_btn);
        Fax_btn = (ImageButton) findViewById(R.id.fax_btn);


        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(Child);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("name").getValue().toString();
                String poste = dataSnapshot.child("Poste").getValue().toString();
                String picture_link = dataSnapshot.child("image").getValue(String.class);
                Picasso.get().load(picture_link).into(Picture);
                PhoneNumber = dataSnapshot.child("Contact").child("phone").getValue().toString();
                FaxNumber = dataSnapshot.child("Contact").child("fax").getValue().toString();
                AdrEmail = dataSnapshot.child("Contact").child("Email").getValue().toString();
                AdrLinkedin = dataSnapshot.child("Contact").child("linkedin").getValue().toString();

                Poste.setText(poste);
                Username.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Social media buttons
        Email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(profile.this,"Email : " + AdrEmail,Toast.LENGTH_LONG).show();
            }
        });

        Phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(profile.this,"Phone : " + PhoneNumber,Toast.LENGTH_LONG).show();
            }
        });

        Fax_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(profile.this,"Fax : " + FaxNumber,Toast.LENGTH_LONG).show();
            }
        });

        //Days Buttons
        Dimanche_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference().child("Users").child(Child).child("Horaires");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        NB.setText("");
                        Matin.setText("Matin :");
                        Soir.setText("Soir");
                        String horaire = dataSnapshot.child("Dimanche").child("matin").getValue().toString();
                        //Si travail le matin
                        if(horaire.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("matin").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture);
                        }
                        String horaire2 = dataSnapshot.child("Dimanche").child("soir").getValue().toString();
                        // Si travail le soir
                        if(horaire2.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("soir").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }else{
                            String picture_link = dataSnapshot.child("pasdetravail").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        Lundi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference().child("Users").child(Child).child("Horaires");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        NB.setText("");
                        Matin.setText("Matin :");
                        Soir.setText("Soir");
                        String horaire = dataSnapshot.child("Lundi").child("matin").getValue().toString();
                        //Si travail le matin
                        if(horaire.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("matin").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture);
                        }
                        String horaire2 = dataSnapshot.child("Lundi").child("soir").getValue().toString();
                        // Si travail le soir
                        if(horaire2.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("soir").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }else{
                            String picture_link = dataSnapshot.child("pasdetravail").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        Mardi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference().child("Users").child(Child).child("Horaires");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        NB.setText("");
                        Matin.setText("Matin :");
                        Soir.setText("Soir");
                        String horaire = dataSnapshot.child("Mardi").child("matin").getValue().toString();
                        //Si travail le matin
                        if(horaire.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("matin").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture);
                        }
                        String horaire2 = dataSnapshot.child("Mardi").child("soir").getValue().toString();
                        // Si travail le soir
                        if(horaire2.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("soir").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }else{
                            String picture_link = dataSnapshot.child("pasdetravail").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        Mercredi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference().child("Users").child(Child).child("Horaires");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        NB.setText("");
                        Matin.setText("Matin :");
                        Soir.setText("Soir");
                        String horaire = dataSnapshot.child("Mercredi").child("matin").getValue().toString();
                        //Si travail le matin
                        if(horaire.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("matin").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture);
                        }
                        String horaire2 = dataSnapshot.child("Mercredi").child("soir").getValue().toString();
                        // Si travail le soir
                        if(horaire2.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("soir").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }else{
                            String picture_link = dataSnapshot.child("pasdetravail").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        Jeudi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference().child("Users").child(Child).child("Horaires");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        NB.setText("");
                        Matin.setText("Matin :");
                        Soir.setText("Soir");
                        String horaire = dataSnapshot.child("Jeudi").child("matin").getValue().toString();
                        //Si travail le matin
                        if(horaire.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("matin").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture);
                        }
                        String horaire2 = dataSnapshot.child("Jeudi").child("soir").getValue().toString();
                        // Si travail le soir
                        if(horaire2.equalsIgnoreCase("Oui")){
                            String picture_link = dataSnapshot.child("soir").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }else{
                            String picture_link = dataSnapshot.child("pasdetravail").getValue(String.class);
                            Picasso.get().load(picture_link).into(DayPicture2);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    public void linkedinClique(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AdrLinkedin));
        startActivity(browserIntent);
    }

    public static void setChild(String child){
        Child = child;
    }
}
