package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeleteFood extends AppCompatActivity implements View.OnClickListener{

    Button btnSearch,btnDelete;
    EditText txtDelete;
    TextView textShow;
    Intent intentGet;
    String UID;
    DatabaseReference dbRef,dbRef2;
    Boolean yes = false,yes2 = false;

    private static final String TAG = "DoctorShowNew";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);

        intentGet = getIntent();
        UID = intentGet.getStringExtra(FoodShowCustomer.UID_SHOW);

        btnSearch = findViewById(R.id.btn_DeleteSearch);
        btnDelete = findViewById(R.id.btn_DeleteDelete);
        txtDelete = findViewById(R.id.txt_deleteText);
        //textShow = findViewById(R.id.txt_ViewDelete);

        btnSearch.setOnClickListener(this);
        btnDelete.setOnClickListener(this);




    }


    @Override
    protected void onResume() {
        super.onResume();

        btnSearch = findViewById(R.id.btn_DeleteSearch);
        btnDelete = findViewById(R.id.btn_DeleteDelete);
        txtDelete = findViewById(R.id.txt_deleteText);
        //textShow = findViewById(R.id.txt_ViewDelete);

        btnSearch.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_DeleteSearch:Search();
                break;
            case R.id.btn_DeleteDelete:Delete();
                break;
        }
    }

    private void initImageBitmaps() {

        dbRef2 = FirebaseDatabase.getInstance().getReference();
        Query deleteQuery2 = dbRef2.child("Order").orderByChild("orderID").equalTo(Integer.parseInt(txtDelete.getText().toString().trim()));

        deleteQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {

                    LocalDateTime now = LocalDateTime.now();
                    String then = deleteSnapshot.child("orderedDateTime").getValue().toString();
                    LocalDateTime thenLocal = LocalDateTime.parse(then);
                    Duration duration = Duration.between(thenLocal, now);


                    if(duration.getSeconds() < 1000) {
                        if (UID.equals(deleteSnapshot.child("userID").getValue().toString())) {
                            yes2 = true;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(yes2 == true) {


            dbRef = FirebaseDatabase.getInstance().getReference();
            Query showQuery = dbRef.child("Order").orderByChild("orderID").equalTo(Integer.parseInt(txtDelete.getText().toString().trim()));

            showQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot showSnapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "initImageBitmaps: started");
                        mImageUrls.add("https://www.howtogeek.com/wp-content/uploads/2020/03/delivery-food.jpg.pagespeed.ce.8e-lIOJeD5.jpg");
                        mNames.add("User ID: " + showSnapshot.child("userID").getValue().toString() + "\n" +
                                "Order ID: " + showSnapshot.child("orderID").getValue().toString() + "\n" +
                                "Food Type: " + showSnapshot.child("foodType").getValue().toString() + "\n" +
                                "Food Amount: " + showSnapshot.child("foodAmount").getValue().toString() + "\n" +
                                "Room No: " + showSnapshot.child("roomNo").getValue().toString() + "\n" +
                                "Order Date: " + showSnapshot.child("orderDate").getValue().toString() + "\n" +
                                "Order Time: " + showSnapshot.child("orderTime").getValue().toString() + "\n" +
                                "Ordered DateTime: " + showSnapshot.child("orderedDateTime").getValue().toString() + "\n" +
                                "Price: " + showSnapshot.child("price").getValue().toString() + "\n\n");
                        initRecyclerView();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: started");
        RecyclerView recyclerView = findViewById(R.id.FoodDelete_recycler_view);
        YasojaRecyclerViewAdapter adapter = new YasojaRecyclerViewAdapter(mNames,mImageUrls,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void Search(){
        Log.d(TAG, "onCreate: started");
        initImageBitmaps();
    }

    public void Delete(){

        dbRef2 = FirebaseDatabase.getInstance().getReference();
        Query deleteQuery2 = dbRef2.child("Order").orderByChild("orderID").equalTo(Integer.parseInt(txtDelete.getText().toString().trim()));

        deleteQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {

                    LocalDateTime now = LocalDateTime.now();
                    String then = deleteSnapshot.child("orderedDateTime").getValue().toString();
                    LocalDateTime thenLocal = LocalDateTime.parse(then);
                    Duration duration = Duration.between(thenLocal, now);


                    if(duration.getSeconds() < 1000) {
                        if (UID.equals(deleteSnapshot.child("userID").getValue().toString())) {
                            yes = true;
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(yes == true) {

            dbRef = FirebaseDatabase.getInstance().getReference();
            Query deleteQuery = dbRef.child("Order").orderByChild("orderID").equalTo(Integer.parseInt(txtDelete.getText().toString().trim()));


            deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot deleteSnapshot : dataSnapshot.getChildren()) {


                        //if(duration.getSeconds() < 1000){
                        //if(UID.equals(deleteSnapshot.child("userID").getValue().toString())){

                        deleteSnapshot.getRef().removeValue();
                        Toast.makeText(getApplicationContext(), "Successfully Removed", Toast.LENGTH_SHORT).show();
                        //}
                        //else{
                        //Toast.makeText(getApplicationContext(), "Not your order", Toast.LENGTH_SHORT).show();
                        //}

                        //}else{
                        //Toast.makeText(getApplicationContext(), "Can Not Be Removed, Duration Passed", Toast.LENGTH_SHORT).show();
                        //}


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
}