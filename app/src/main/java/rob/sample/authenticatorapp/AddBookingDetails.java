package rob.sample.authenticatorapp;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddBookingDetails extends AppCompatActivity  {

    public static final String USER_ID = "net.simplifiedcoding.firebasedatabaseexample.artistname";

    EditText dateTXT,dateTXT2,adultNo,childNo;
    Button btnSubmit;
    TextView Roomno;
    TextView Rtype;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    //ImageView cal,cal2;


    private int nDate,nMonth,nYear,nDate2,nMonth2,nYear2;

    BookRoom bookR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking_details);

      /*  final loadingDialog load = new loadingDialog(AddBookingDetails.this);
        load.startLoadingDailog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load.dismissDailog();
            }
        },1000);*/


        adultNo = (EditText) findViewById(R.id.adultNo);
        childNo = (EditText) findViewById(R.id.childNo);
        Roomno = (TextView) findViewById(R.id.roomNo1);
        Roomno.setText((getIntent().getStringExtra("roomNo")));
        Rtype = (TextView) findViewById(R.id.lb_Rtype2);
        Rtype.setText((getIntent().getStringExtra("RType")));

        btnSubmit = (Button) findViewById(R.id.btn_submit);

        dateTXT = (EditText) findViewById(R.id.date);
        dateTXT2 = (EditText) findViewById(R.id.date2);




        bookR = new BookRoom();




        dateTXT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar Cal = Calendar.getInstance();
                nDate = Cal.get(Calendar.DATE);
                nMonth = Cal.get(Calendar.MONTH);
                nYear = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBookingDetails.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateTXT.setText(date + "-" + month + "-" + year);
                    }
                }, nYear, nMonth, nDate);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }

        });


        dateTXT2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar Cal = Calendar.getInstance();
                nDate2 = Cal.get(Calendar.DATE);
                nMonth2 = Cal.get(Calendar.MONTH);
                nYear2 = Cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBookingDetails.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        dateTXT2.setText(date + "-" + month + "-" + year);
                    }
                }, nYear2, nMonth2, nDate2);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }

        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MY","my", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference("Booking");
                try {
                    if (TextUtils.isEmpty(adultNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Adult No", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(childNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Child No", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(dateTXT.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Check-In Date", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(dateTXT2.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Empty Check-Out Date", Toast.LENGTH_SHORT).show();
                    else {
                        String adNo = adultNo.getText().toString().trim();
                        String chNo = childNo.getText().toString().trim();
                        String dt1 = dateTXT.getText().toString().trim();
                        String dt2 = dateTXT2.getText().toString().trim();
                        String rNo = getIntent().getStringExtra("roomNo");
                        String id = dbRef.push().getKey();
                        String Uid=getIntent().getStringExtra("ID");

                        boolean yes = checkNo(adNo,chNo);

                        if(yes == true) {

                            BookRoom Brooms = new BookRoom(id, rNo, adNo, chNo, dt1, dt2, Uid);

                            dbRef.child(id).setValue(Brooms);

                            dbRef.push().setValue(bookR);

                            Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
                            clearControl();
                            Intent intent = new Intent(AddBookingDetails.this, Home2.class);
                            startActivity(intent);

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(AddBookingDetails.this, "MY");
                            builder.setContentTitle("Thank you for booking.");
                            builder.setContentText("Room Number - " + rNo + "  Successfully Booked");
                            builder.setSmallIcon(R.drawable.ic_tick);
                            builder.setAutoCancel(true);

                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AddBookingDetails.this);
                            managerCompat.notify(1, builder.build());
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Only maximum of 3 child and 4 adults", Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (
                        NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                }


            }

        });


    }

    public boolean checkNo(String adult,String child){
        int ad = Integer.parseInt(adult);
        int ch = Integer.parseInt(child);

        if(ch >3 || ad >4){
            return false;
        }else{
            return true;
        }
    }




 /*  public void onSubmitClick(View view){

       dbRef = FirebaseDatabase.getInstance().getReference().child("BookingRoom");
        try {
            if (TextUtils.isEmpty(adultNo.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Adult No", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(childNo.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Child No", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(dateTXT.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Check-In Date", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(dateTXT2.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Check-Out Date", Toast.LENGTH_SHORT).show();
            else {
                bookR.setAdultNo(Integer.parseInt(adultNo.getText().toString().trim()));
                bookR.setChildNo(Integer.parseInt(childNo.getText().toString().trim()));
                bookR.setCheckIn(dateTXT.getText());
                bookR.setCheckOut( dateTXT2.getText());
                dbRef.child("BookingRoom").setValue(bookR);
                Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
                clearControl();
            }
        }
        catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
        }
    }*/








        private void clearControl() {
      adultNo.setText("");
      childNo.setText("");
      dateTXT.setText("");
      dateTXT2.setText("");
    }


}