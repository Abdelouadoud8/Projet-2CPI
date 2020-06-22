package com.example.projet_2cpi;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
// activation
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.security.auth.Subject;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final int REQUEST_CODE = 123;
    //private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    AutoCompleteTextView mSearchField ;
    private static final String[] ResultSuggestions = new String[] {};
    //Variables for nav-bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private DatabaseReference mUserDatabase;
    Query firebaseSearchQuery;
    FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter;
    List<Users> result;
    private String Language_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*****************************HOOKS*********************************/
        drawerLayout =findViewById(R.id.drawer_layout);
        navigationView =findViewById(R.id.nav_view);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        mSearchField = findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);
        mResultList = (RecyclerView) findViewById(R.id.result_list);

        /*****************************Autorisation*********************************/

        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.CAMERA) ){
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CAMERA
                        }, 1);

            }else{
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CAMERA
                        }, 1);
            }
        }

        /*******************************SEARCH*********************************/

        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Toast.makeText(HomeActivity.this, R.string.connected, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(HomeActivity.this, R.string.not_connected, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        }); // test if connected or not

        result = new ArrayList<Users>();
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchResultat();
            }
        });

        /*******************************SEARCH SUGGESTION*******************************/
        String[] ResultSuggestions = getResources().getStringArray(R.array.ResultSuggestions);

        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,ResultSuggestions);
        mSearchField.setAdapter(adapter);

        /*******************************NAV-BAR*********************************/
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

        /*******************************SET DEFAULT LANGUAGE FR*********************************/
        Language_test = Languages.getLanguage();
        //default language
        if (Language_test == null){
            Languages.setLanguage("fr");
        }


    }

    /*******************************FOR AUTORISATION*********************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if ((ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        && (ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    passto();

                }
            } else {
                passto();
            }
        }
    }

    private void passto(){
        Intent intent = new Intent(this, activation.class);
        startActivity(intent);
    }

    /*******************************FOR SEARCH*********************************/
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

    static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        void setDetails(Context ctx, String userName, String userStatus, String userImage){

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView user_status = (TextView) mView.findViewById(R.id.status_text);
            ImageView user_image = (ImageView) mView.findViewById(R.id.profile_image);
            user_name.setText(userName);
            user_status.setText(userStatus);
            user_image.setImageResource(R.drawable.ic_launcher_foreground);

            Glide.with(ctx).load(userImage).into(user_image);
        }
    }

    //search function
    public void SearchResultat(){
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

    @Override //Search when click on Enter button
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                SearchResultat();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }


    /*******************************FOR NAV-BAR*********************************/
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
                break;
            case R.id.nav_about:
                OpenActivity(aboutus.class);
                break;
            case R.id.nav_contact:
                ContactUs(this.navigationView);
                break;
            case R.id.nav_share:
                Share();
                break;
            case R.id.nave_rate:
                OpenActivity(Rating.class);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /*******************************FOR LANGUAGE BUTTON*********************************/
    private void showChangeLanguageDialogue() {
        final String [] listItems={"Français","English","عربي"};
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(HomeActivity.this);
        mBuilder.setTitle(getString(R.string.choose_languages));
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            Languages.setLanguage("fr");
                            setLocale("fr");
                            recreate();
                        } else if (i == 1) {
                            Languages.setLanguage("en");
                            setLocale("en");
                            recreate();
                        } else if (i == 2) {
                            Languages.setLanguage("ar");
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

    /*******************************FOR SHARE BUTTON*********************************/
    private void Share(){
        Intent SharingIntent = new Intent(Intent.ACTION_SEND);
        SharingIntent.setType("text/plain");
        String ShareBody = "partager via :";
        String ShareSubject = "On vous invite à télécahrger l'application Guiddini on appuiant sur ce lien : LIEN MZL\"";
        //SharingIntent.putExtra(Intent.EXTRA_TEXT,ShareBody);
        SharingIntent.putExtra(Intent.EXTRA_SUBJECT,ShareSubject);
        startActivity(Intent.createChooser(SharingIntent,"Partager via :"));
    }
    //Share button

    /*****************************NAVIGATION-BAR BUTTONS FUNCTIONS START*********************************/
    //contact us button
    public void ContactUs(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:guiddini@contact.dz"));
        startActivity(browserIntent);
    }


    /*****************************OPEN ACTIVITY FUNCTION*********************************/
    public void OpenActivity(Class page){
        Intent intent = new Intent(HomeActivity.this,page);
        startActivity(intent);
    }


}

