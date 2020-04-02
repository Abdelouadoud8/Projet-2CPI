package com.example.projet_2cpi;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projet_2cpi.MainActivity;
import com.example.projet_2cpi.R;
import com.example.projet_2cpi.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MainActivity.UsersViewHolder> {

    private static String USERNAME;
    private final List<Users> mData;
    Context context;
    DatabaseReference reff;
    //public String USERNAME;



    RecyclerViewAdapter(List<Users> data)
    {mData = data;}

    @Override
    public MainActivity.UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("creat","ok");
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
        return new MainActivity.UsersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainActivity.UsersViewHolder holder, final int position)
    {
        Log.i("bind","ok");

        holder.setDetails(context,mData.get(position).getName(),mData.get(position).getStatus(),mData.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USERNAME = mData.get(position).getName();
                Toast.makeText(v.getContext(),USERNAME,Toast.LENGTH_LONG).show();
                //openProfilActivity();
                // Tester nrecuperer username mel BDD
                /* reff = FirebaseDatabase.getInstance().getReference("Users");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String username = dataSnapshot.child("01").child("name").getValue().toString();
                        //USERNAME = username;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }); */
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();}

    public static String getUSERNAME(){
        return USERNAME;
    }
}

