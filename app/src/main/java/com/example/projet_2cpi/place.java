package com.example.projet_2cpi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class place extends AppCompatActivity {
    TextView Cycle, Username, NoEmployer,EmployerStat, Description;
    ImageView DescriptionShape, Image_btn,ImageForButton;
    ImageButton Go_profile_btn,Email_btn, Phone_btn, Linkedin_btn, Fax_btn;;
    DatabaseReference reff;
    String Child, make_child, Employe;
    String PhoneNumber, FaxNumber, AdrEmail, AdrLinkedin;
    String Language2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity);

        Language2 = Languages.getLanguage();
        Child = RecyclerViewAdapter.getUSERNAME();
        //Text Views
        Cycle = (TextView)findViewById(R.id.cycle);
        Username = (TextView)findViewById(R.id.username2);
        NoEmployer =(TextView)findViewById(R.id.noEmployer);
        EmployerStat =(TextView)findViewById(R.id.employerStat);
        Description = (TextView) findViewById(R.id.description);
        //Image Views
        DescriptionShape = (ImageView)findViewById(R.id.descripton_shape);
        Image_btn = (ImageView)findViewById(R.id.image_btn);
        ImageForButton = (ImageView)findViewById(R.id.ImageForBtn);
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
                String employe_name = "";
                String cycle = "";
                String description = "";

                //String cycle = dataSnapshot.child(Child).child("cycle").getValue().toString();
                String employe = dataSnapshot.child(Child).child("employe").getValue().toString();
                //String employe_name = dataSnapshot.child(employe).child("ar").child("name").getValue().toString();
                if (!employe.equals("Null")){
                    switch (Language2) {
                        case "fr":
                            employe_name = dataSnapshot.child(employe).child("fr").child("name").getValue().toString();
                            break;
                        case "en":
                            employe_name = dataSnapshot.child(employe).child("en").child("name").getValue().toString();
                            break;
                        case "ar":
                            employe_name = dataSnapshot.child(employe).child("ar").child("name").getValue().toString();
                            break;
                    }
                }
                switch (Language2) {
                    case "fr":
                        cycle = dataSnapshot.child(Child).child("fr").child("cycle").getValue().toString();
                        break;
                    case "en":
                        cycle = dataSnapshot.child(Child).child("en").child("cycle").getValue().toString();
                        break;
                    case "ar":
                        cycle = dataSnapshot.child(Child).child("ar").child("cycle").getValue().toString();
                        break;
                }
                switch (Language2) {
                    case "fr":
                        description = dataSnapshot.child(Child).child("fr").child("description").getValue().toString();
                        break;
                    case "en":
                        description = dataSnapshot.child(Child).child("en").child("description").getValue().toString();
                        break;
                    case "ar":
                        description = dataSnapshot.child(Child).child("ar").child("description").getValue().toString();
                        break;
                }

                //String username = dataSnapshot.child(employe).child("name").getValue().toString();
                Employe = employe;
                make_child = employe;

                Description.setText(description);
                Cycle.setText(cycle);
                if(!Employe.equals("Null")){
                    Username.setText(employe_name);
                    String picture_link = dataSnapshot.child(employe).child("image").getValue(String.class);
                    Picasso.get().load(picture_link).into(Image_btn);
                    String picture2_link = "";
                    //String picture2_link = dataSnapshot.child("imageforbuttonfr").getValue(String.class);
                    switch (Language2) {
                        case "fr":
                            picture2_link = dataSnapshot.child("imageforbutton").getValue(String.class);
                            break;
                        case "en":
                            picture2_link = dataSnapshot.child("imageforbutton").getValue(String.class);
                            break;
                        case "ar":
                            picture2_link = dataSnapshot.child("imageforbuttonar").getValue(String.class);
                            break;
                    }
                        Picasso.get().load(picture2_link).into(ImageForButton);
                    String picture3_link = dataSnapshot.child(employe).child("image").getValue(String.class);
                    Picasso.get().load(picture3_link).into(Image_btn);
                    EmployerStat.setText(R.string.Employer);
                }else{
                    NoEmployer.setText(R.string.Aller_vers_cet_endroit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!Employe.equals("Null")){
                    PhoneNumber = dataSnapshot.child(Employe).child("Contact").child("phone").getValue().toString();
                    FaxNumber = dataSnapshot.child(Employe).child("Contact").child("fax").getValue().toString();
                    AdrEmail = dataSnapshot.child(Employe).child("Contact").child("Email").getValue().toString();
                    AdrLinkedin = dataSnapshot.child(Employe).child("Contact").child("linkedin").getValue().toString();
                }else{
                    PhoneNumber = "Null";
                    FaxNumber = "Null";
                    AdrEmail = "Null";
                    AdrLinkedin = "Null";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Go_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!make_child.equals("Null")) {
                    profile.setChild(make_child);
                    RecyclerViewAdapter.setUSERNAME(make_child);
                    openProfilActivity2();
                }
            }
        });

    }

    public void openProfilActivity2(){
        Intent intent = new Intent(this,profile.class);
        startActivity(intent);


    }

    public void EmailClique(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AdrEmail));
        startActivity(browserIntent);
    }

    public void linkedinClique(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AdrLinkedin));
        startActivity(browserIntent);
    }

    public void FaxClique(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(FaxNumber));
        startActivity(browserIntent);
    }

    public void PhoneClique(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PhoneNumber));
        startActivity(browserIntent);
    }


}
