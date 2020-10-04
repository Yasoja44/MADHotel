package com.example.madapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditBookingDetails extends AppCompatActivity {

    TextView Roomno;
    Button btnEdit;
    EditText dateTXT,dateTXT2,adultNo,childNo;

    private int nDate,nMonth,nYear,nDate2,nMonth2,nYear2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking_details);

        btnEdit = (Button) findViewById(R.id.btn_update);

        adultNo = (EditText) findViewById(R.id.adultNo2);
        adultNo.setText((getIntent().getStringExtra("Adultno")));

        childNo = (EditText) findViewById(R.id.childNo2);
        childNo.setText((getIntent().getStringExtra("Childno")));

        Roomno = (TextView) findViewById(R.id.roomNo2);
        Roomno.setText((getIntent().getStringExtra("Roomno")));

        dateTXT = (EditText) findViewById(R.id.date3);
        dateTXT.setText((getIntent().getStringExtra("Checkin")));

        dateTXT2 = (EditText) findViewById(R.id.date4);
        dateTXT2.setText((getIntent().getStringExtra("Checkout")));


        dateTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar Cal = Calendar.getInstance();
                nDate = Cal.get(Calendar.DATE);
                nMonth = Cal.get(Calendar.MONTH);
                nYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditBookingDetails.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateTXT.setText(date + "-" + month + "-" + year);
                    }
                }, nYear, nMonth, nDate);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }

        });


        dateTXT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar Cal = Calendar.getInstance();
                nDate2 = Cal.get(Calendar.DATE);
                nMonth2 = Cal.get(Calendar.MONTH);
                nYear2 = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditBookingDetails.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateTXT2.setText(date + "-" + month + "-" + year);
                    }
                }, nYear2, nMonth2, nDate2);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }

        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = getIntent().getStringExtra("Bookingid");
                String adNo = adultNo.getText().toString().trim();
                String chNo = childNo.getText().toString().trim();
                String dt1 = dateTXT.getText().toString().trim();
                String dt2 = dateTXT2.getText().toString().trim();
                String rno =Roomno.getText().toString().trim();


               // if (!TextUtils.isEmpty(name)) {
                    updateArtist(id, rno, adNo,chNo,dt1,dt2);
                Intent intent = new Intent(EditBookingDetails.this,Booking_DetailsList.class);
                startActivity(intent);

            }
        });


    }

    private boolean updateArtist(String id, String roomNo,String adultNo,String childNo,String checkIn,String checkOut) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Booking").child(id);
        String uid="u1";
        //updating artist
        BookRoom BRooms = new BookRoom(id, roomNo, adultNo,childNo,checkIn,checkOut,uid);
        dR.setValue(BRooms);
        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }



}