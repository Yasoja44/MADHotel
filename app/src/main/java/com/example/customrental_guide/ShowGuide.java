package com.example.customrental_guide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowGuide extends AppCompatActivity {

    private static final String TAG = "Guide";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_guide);

        initImageBitmaps();
    }

    private void initImageBitmaps() {

        dbRef = FirebaseDatabase.getInstance().getReference();
        Query showQuery = dbRef.child("ManageGuide").orderByChild("gemail");

        showQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot showSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, "initImageBitmaps: started");
                    mImageUrls.add("https://www.checkfront.com/wp-content/uploads/2020/01/how-to-be-the-best-tour-guide.jpg");
                    mNames.add("Guide Area: "+showSnapshot.child("GArea").getValue().toString() + "\n"+
                            "Guide Name: "+showSnapshot.child("GName").getValue().toString() + "\n"+
                            "Guide Destination: "+showSnapshot.child("GDes").getValue().toString() + "\n" +
                            "Contact No: " + showSnapshot.child("GCon").getValue().toString() + "\n" +
                            "E-mail: " + showSnapshot.child("GEmail").getValue().toString() + "\n" +
                             "\n\n");
                    initRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: started");
        RecyclerView recyclerView = findViewById(R.id.vehi_recycler_view);
        JanithRecyclerViewAdapter adapter = new JanithRecyclerViewAdapter(mNames,mImageUrls,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}