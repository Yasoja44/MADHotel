package rob.sample.authenticatorapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EditFood extends AppCompatActivity implements View.OnClickListener {

    EditText orderID,foodName,foodAmount,roomNo,orderDate,orderTime;
    Button btnEdit;
    ImageButton imgbtnDate,imgbtnTime;
    DatabaseReference dbRef,dbRef2;
    Map<String,Object> updateMap;
    Double unitPrice;
    Intent intentGet;
    String UID;
    boolean yes = false;
    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;

    public static String UID_EDIT = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        orderID = findViewById(R.id.txt_editOrderID);
        orderDate = findViewById(R.id.txt_editDate);
        orderTime = findViewById(R.id.txt_editTime);
        imgbtnDate = findViewById(R.id.imgbtn_EditFooddatePick);
        imgbtnTime = findViewById(R.id.imgbtn_EditFoodtimePick);

        btnEdit = findViewById(R.id.btn_editEdit);


        btnEdit.setOnClickListener(this);
        imgbtnDate.setOnClickListener(this);
        imgbtnTime.setOnClickListener(this);


        intentGet = getIntent();
        UID = intentGet.getStringExtra(FoodShowCustomer.UID_SHOW);

    }

    @Override
    protected void onResume() {
        super.onResume();

        orderID = findViewById(R.id.txt_editOrderID);
        orderDate = findViewById(R.id.txt_editDate);
        orderTime = findViewById(R.id.txt_editTime);
        btnEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_editEdit:Edit();
                break;
            case R.id.imgbtn_EditFooddatePick: DateChoose();
                break;
            case R.id.imgbtn_EditFoodtimePick: TimeChoose();
                break;
        }
    }

    public void DateChoose(){
        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(EditFood.this,new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int nYear, int nMonth, int nDate) {
                orderDate.setText(nDate + "-"+(nMonth+1) +"-"+ nYear);
            }
        },year,month,day);
        dpd.show();
    }
    public void TimeChoose(){
        c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMin = c.get(Calendar.MINUTE);

        tpd = new TimePickerDialog(EditFood.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int nhour, int nMin) {
                orderTime.setText(nhour + ":"+nMin);
            }
        },mHour,mMin,false);
        tpd.show();
    }

    public void Delete(){
        Intent next = new Intent(this,DeleteFood.class);
        next.putExtra(UID_EDIT,UID);
        startActivity(next);


    }

    public void Edit() {

        dbRef2 = FirebaseDatabase.getInstance().getReference();
        Query updateQuery2 = dbRef2.child("Order").orderByChild("orderID").equalTo(Integer.parseInt(orderID.getText().toString().trim()));

        dbRef = FirebaseDatabase.getInstance().getReference();
        Query updateQuery = dbRef.child("Order").orderByChild("orderID").equalTo(Integer.parseInt(orderID.getText().toString().trim()));

        String dateTemp = orderDate.getText().toString().trim();
        String timeTemp = orderTime.getText().toString().trim();

        try {

            if (dateTemp.length() <=8) {
                Toast.makeText(getApplicationContext(), "Invalid Date, Follow DD-MM-YYYY format", Toast.LENGTH_SHORT).show();
            } else if (timeTemp.length() <=3) {
                Toast.makeText(getApplicationContext(), "Invalid Time, Follow HH:MM format", Toast.LENGTH_SHORT).show();
            } else {


                updateMap = new HashMap<String, Object>();
                updateMap.put("orderID", orderID.getText().toString().trim());
                updateMap.put("orderDate", orderDate.getText().toString().trim());
                updateMap.put("orderTime", orderTime.getText().toString().trim());

                updateQuery2.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot updateSnapshot : dataSnapshot.getChildren()) {

                            LocalDateTime now = LocalDateTime.now();
                            String then = updateSnapshot.child("orderedDateTime").getValue().toString();
                            LocalDateTime thenLocal = LocalDateTime.parse(then);

                            Duration duration = Duration.between(thenLocal, now);

                            if (duration.getSeconds() < 1000) {
                                if (UID.equals(updateSnapshot.child("userID").getValue().toString())) {
                                    yes = true;
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if (yes == true) {

                    updateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot updateSnapshot : dataSnapshot.getChildren()) {
                                updateSnapshot.getRef().updateChildren(updateMap);
                                Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                                clearControls();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        }catch (NumberFormatException | IndexOutOfBoundsException nfe){
            Toast.makeText(getApplicationContext(), "Invalid Date,Time or Amount", Toast.LENGTH_SHORT).show();
        }
    }


    private void clearControls(){
        orderID.setText("");
        orderDate.setText("");
        orderTime.setText("");
    }
}