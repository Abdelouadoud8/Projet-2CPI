package com.example.projet_2cpi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RecyclerView mResultList;
    // Essaye d'ouvrir la page profil
    private Button open_profil_btn;


    private DatabaseReference mUserDatabase;
    Query firebaseSearchQuery;
    FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter;
    List<Users> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");
        //Button open profil page
        /* open_profil_btn = findViewById(R.id.open_profile_btn);
        open_profil_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RecyclerViewAdapter.getGENRE().equals("Person")){
                    openProfilActivity();
                }else if(RecyclerViewAdapter.getGENRE().equals("Place")){
                    openPlaceActivity2();
                }

            }
        }); */

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
        //result.add(new Users("Hiver", "Vacances de février durent 2 semaines", "R.drawable.hiver"));
        //mResultList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));






        // mResultList.setHasFixedSize(true);

/*
        firebaseSearchQuery = mUserDatabase.orderByChild("name").equalTo("Djouad Kawther");
        //.startAt("Djouad Kawth").endAt("Djouad Kawth" + "\uf8ff");


        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(firebaseSearchQuery, Users.class)
                        .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>
                (options) {
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_layout, parent, false);
                Toast.makeText(MainActivity.this, "create", Toast.LENGTH_SHORT).show();

                return new UsersViewHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(UsersViewHolder holder, int position, Users model) {
                holder.setDetails(getApplicationContext(),model.name,model.status,model.image);
                Toast.makeText(MainActivity.this, "bind", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                //firebaseRecyclerAdapter.notifyDataSetChanged();
            }
            @Override
            public int getItemCount() {
                return  0;
            }

        };

        mResultList.setAdapter(firebaseRecyclerAdapter);



*/

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
                //result.add(new Users("o","k","k"));
                //mResultList.setAdapter(new RecyclerViewAdapter(result));
            }
        });
        // result.add(new Users("o","k","k"));
        // mResultList.setLayoutManager(new LinearLayoutManager(this));
        // mResultList.setAdapter(new RecyclerViewAdapter(result));
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


    /* public void openProfilActivity(){
        Intent intent = new Intent(this,profile.class);
        startActivity(intent);
    }

    public void openPlaceActivity2(){
        Intent intent = new Intent(this,place.class);
        startActivity(intent);
    } */
}

