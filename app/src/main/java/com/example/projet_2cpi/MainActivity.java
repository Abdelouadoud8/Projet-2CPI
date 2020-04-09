package com.example.projet_2cpi;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    //Variables for nav-bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    private DatabaseReference mUserDatabase;
    Query firebaseSearchQuery;
    FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter;
    List<Users> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);


        /*----------------------Hooks----------------------*/
        drawerLayout =findViewById(R.id.drawer_layout);
        navigationView =findViewById(R.id.nav_view);
        toolbar =(Toolbar) findViewById(R.id.toolbar);

        /*--------------------------SEARCH--------------------------*/

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "not connected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        mResultList = (RecyclerView) findViewById(R.id.result_list);

        result = new ArrayList<Users>();
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.clear();
                String searchText = mSearchField.getText().toString();
                // firebaseUserSearch(searchText);
                Query query = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");;
                // result = new ArrayList<Users>();
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                                Users u = messageSnapshot.getValue(Users.class);
                                result.add(u);
                                //  Toast.makeText(MainActivity.this,result.size()+"", Toast.LENGTH_SHORT).show();
                                mResultList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mResultList.setAdapter(new RecyclerViewAdapter(result));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        /*--------------------------SEARCH--------------------------*/

        /*--------------------------NAV-BAR--------------------------*/

        //Toolbar
        setSupportActionBar(toolbar);


        //Navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        /*--------------------------NAV-BAR--------------------------*/

    }


    @Override
    protected void onStart() {
        super.onStart();
        // firebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // firebaseRecyclerAdapter.stopListening();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    // View Holder Class

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(Context ctx,String userName, String userStatus, String userImage){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_status = (TextView) mView.findViewById(R.id.status_text);
            ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);
            user_name.setText(userName);
            user_status.setText(userStatus);
            user_image.setImageResource(R.drawable.ic_launcher_foreground);

            Glide.with(ctx).load(userImage).into(user_image);
        }
    }

    //for NAV-bar
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_how:
                /*  Intent intent = new Intent(MainActivity.this,HomItWorks.class);
                startActivity(intent); */
                break;
            case R.id.nav_language:
                showChangeLanguageDialogue();
            case R.id.nav_about:
                break;
            case R.id.nav_contact:
                break;
            case R.id.nav_share:
                break;
            case R.id.nave_rate:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showChangeLanguageDialogue() {
        final String [] listItems={"Français","English","عربي"};
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("choose language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            setLocale("fr");
                            recreate();
                        } else if (i == 1) {
                            setLocale("en");
                            recreate();
                        } else if (i == 2) {
                            setLocale("ar");
                            recreate();
                        }
                        dialogInterface.dismiss();
                    }
                }
        );
        AlertDialog mDialog=mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String langue) {
        Locale locale=new Locale(langue);
        Locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("Ma langue",langue);
        editor.apply();
    }
    public void loadLocale(){
        SharedPreferences pref=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String langue=pref.getString("Ma langue","");
        setLocale(langue);
    }
}

