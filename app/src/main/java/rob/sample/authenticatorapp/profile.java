package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class profile extends AppCompatActivity {

    TextView name,email,phone;
    Button booking;
    DatabaseReference dbref;
    registerClass rClass;
    String UID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView)findViewById(R.id.pname);
        email =(TextView)findViewById(R.id.pemail);
        phone = (TextView)findViewById(R.id.pphone);
        booking = (Button)findViewById(R.id.btn_bhis);

        Intent intent = getIntent();
        UID = intent.getStringExtra(Home2.USER_ID);
        Toast.makeText(getApplicationContext(), ""+UID, Toast.LENGTH_SHORT).show();


      //  button.setOnClickListener(new View.OnClickListener() {
         //   @Override
          //  public void onClick(View v) {
                dbref= FirebaseDatabase.getInstance().getReference();
                Query showQuery = dbref.child("registerClass").orderByChild("cEmail").equalTo(UID);
                showQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot showSnapshot: dataSnapshot.getChildren()) {

                                String cfullName = showSnapshot.child("cfullName").getValue().toString();
                                String cEmail = showSnapshot.child("cEmail").getValue().toString();
                                String cPhone = showSnapshot.child("cPhone").getValue().toString();


                                name.setText(cfullName);
                                email.setText(cEmail);
                                phone.setText(cPhone);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                booking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(profile.this, Booking_DetailsList.class);
                        intent.putExtra("ID",UID);
                        startActivity(intent);
                    }
                });
                }


}