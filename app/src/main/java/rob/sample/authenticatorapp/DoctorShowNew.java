package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorShowNew extends AppCompatActivity implements View.OnClickListener{

    Button btnEditDelete;
    DatabaseReference dbRef;
    private static final String TAG = "DoctorShowNew";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_show_new);


        btnEditDelete = findViewById(R.id.btn_dEditDelete);

        btnEditDelete.setOnClickListener(this);

        Log.d(TAG, "onCreate: started");
        initImageBitmaps();



    }

    protected void onResume() {
        super.onResume();
        //txtShow = findViewById(R.id.txt_viewDetails);
        btnEditDelete = findViewById(R.id.btn_dEditDelete);
        btnEditDelete.setOnClickListener(this);
    }

    private void initImageBitmaps() {
        Log.d(TAG, "onCreate: started");
        dbRef = FirebaseDatabase.getInstance().getReference();
        Query showQuery = dbRef.child("Doctor").orderByChild("doctorID");

        showQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot showSnapshot: dataSnapshot.getChildren()) {
                    Log.d(TAG, "initImageBitmaps: started");
                    mImageUrls.add("https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1489&q=80");
                    mNames.add("Doctor ID: "+showSnapshot.child("doctorID").getValue().toString() + "\n"+
                            "Doctor Name: "+showSnapshot.child("doctorName").getValue().toString() + "\n" +
                            "Doctor Speciality: " + showSnapshot.child("doctorSpeciality").getValue().toString() + "\n" +
                            "Doctor Address: " + showSnapshot.child("doctorAddress").getValue().toString() + "\n" +
                            "Doctor Phone: " + showSnapshot.child("doctorPhone").getValue().toString() + "\n" +
                            "Doctor E-mail: " + showSnapshot.child("doctorEmail").getValue().toString() + "\n\n");
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
        RecyclerView recyclerView = findViewById(R.id.doctor_recycler_view);
        YasojaRecyclerViewAdapter adapter = new YasojaRecyclerViewAdapter(mNames,mImageUrls,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dEditDelete: EditDelete();
                break;
        }
    }

    public void EditDelete(){
        Intent intent2 = new Intent(this,EditDoctor.class);
        //intent.putExtra(EXTRA_MESSAGE, String.valueOf(count));
        startActivity(intent2);
    }
}