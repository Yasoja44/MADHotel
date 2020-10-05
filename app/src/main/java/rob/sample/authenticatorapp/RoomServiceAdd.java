package rob.sample.authenticatorapp;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import rob.sample.authenticatorapp.Database.FoodsMaster;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class RoomServiceAdd extends AppCompatActivity implements View.OnClickListener {

    TextView txtFoodType;
    EditText txtAmount,txtRoomNo,txtDate,txtTime;
    Button btnOrder;
    ImageButton imgbtnDate,imgbtnTime;
    FoodsMaster ord;
    int count,foodno,min = 1,max = 100;
    DatabaseReference dbRef;
    Double unitPrice;
    String UID;
    Calendar c;
    DatePickerDialog dpd;
    TimePickerDialog tpd;

    private static final String CHANNEL_ID = "www";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service_add);

        txtFoodType = findViewById(R.id.txt_foodType);

        txtAmount = findViewById(R.id.txt_addAmount);
        txtRoomNo = findViewById(R.id.txt_addRoomNo);
        txtDate = findViewById(R.id.txt_addDate);
        txtTime = findViewById(R.id.txt_addTime);

        btnOrder = findViewById(R.id.btn_addOrder);
        //btnShow = findViewById(R.id.btn_addShowCus);
        imgbtnDate = findViewById(R.id.imgbtn_AddFooddatePick);
        imgbtnTime = findViewById(R.id.imgbtn_AddFoodtimePick);

        ord = new FoodsMaster();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        UID = extras.getString("UID_CHOOSE");
        foodno = extras.getInt("EXTRA_MESSAGE");

        Toast.makeText(getApplicationContext(), "Empty Time "+UID + foodno, Toast.LENGTH_SHORT).show();



        if(foodno == 1){
            txtFoodType.setText("PIZZA");
            unitPrice = 1000.0;
        }
        else if(foodno == 2){
            txtFoodType.setText("BREAD");
            unitPrice = 200.0;
        }else{
            txtFoodType.setText("RICE");
            unitPrice = 500.0;
        }



        btnOrder.setOnClickListener(this);
        //btnShow.setOnClickListener(this);
        imgbtnDate.setOnClickListener(this);
        imgbtnTime.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name_yaso);
            String description = getString(R.string.channel_description_yaso);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnOrder.setOnClickListener(this);
        imgbtnDate.setOnClickListener(this);
        imgbtnTime.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addOrder: Add();
                break;
            case R.id.imgbtn_AddFooddatePick: DateChoose();
                break;
            case R.id.imgbtn_AddFoodtimePick: TimeChoose();
                break;
            //case R.id.btn_addShowCus: Show();
                //break;
        }
    }

    public void DateChoose(){
        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        dpd = new DatePickerDialog(RoomServiceAdd.this,new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int nYear, int nMonth, int nDate) {
                txtDate.setText(nDate + "-"+(nMonth+1) +"-"+ nYear);
            }
        },year,month,day);
        dpd.show();

    }

    public void TimeChoose(){
        c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMin = c.get(Calendar.MINUTE);

        tpd = new TimePickerDialog(RoomServiceAdd.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int nhour, int nMin) {
                txtTime.setText(nhour + ":"+nMin);
            }
        },mHour,mMin,false);
        tpd.show();
    }

    public Double findTotal(double UP,double amount){
        Double Total = UP * amount;
        if(Total >= 3000.0){
            Total -= Total*(1/10.0);
        }
        return Total;
    }


    public void Add(){
        dbRef = FirebaseDatabase.getInstance().getReference().child("Order");
        try {
            if (TextUtils.isEmpty(txtAmount.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Amount", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtRoomNo.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Room No", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtDate.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Date", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtTime.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Time", Toast.LENGTH_SHORT).show();
            else {

                count = (int)(Math.random() * (max - min + 1) + min);

                ord.setOrderID(count);
                ord.setUserID(UID);
                ord.setFoodType(txtFoodType.getText().toString().trim());
                ord.setFoodAmount(Integer.parseInt(txtAmount.getText().toString().trim()));
                ord.setRoomNo(txtRoomNo.getText().toString().trim());
                ord.setOrderDate(txtDate.getText().toString().trim());
                ord.setOrderTime(txtTime.getText().toString().trim());

                LocalDateTime now = LocalDateTime.now();
                ord.setOrderedDateTime(now.toString().trim());

                Double Total = findTotal(unitPrice,Double.parseDouble(txtAmount.getText().toString().trim()));
                ord.setPrice(Total);


                String dateTemp = txtDate.getText().toString().trim();
                String timeTemp = txtTime.getText().toString().trim();

                if(dateTemp.length() <= 8){
                    Toast.makeText(getApplicationContext(), "Invalid Date, Follow DD-MM-YYYY format"+dateTemp.length(), Toast.LENGTH_SHORT).show();
                }else if( timeTemp.length()  <= 3){
                    Toast.makeText(getApplicationContext(), "Invalid Time, Follow HH:MM format", Toast.LENGTH_SHORT).show();
                }else{
                    dbRef.push().setValue(ord);
                    Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
                    clearControls();


                    String message1 = txtFoodType.getText().toString();

                    Intent intent = new Intent(this, Notification.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    //PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, , 0);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.showorder2)
                            .setContentTitle("ORDER")
                            .setContentText(message1 +" Has Been Successfully Ordered  Rs." + Total)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(0, builder.build());
                }


            }
        } catch (NumberFormatException | IndexOutOfBoundsException nfe) {
            Toast.makeText(getApplicationContext(), "Invalid Date,Time or Amount", Toast.LENGTH_SHORT).show();
        }
    }

    public void Show(){
        Intent intent2 = new Intent(this,FoodShowAdmin.class);

        startActivity(intent2);
    }

    private void clearControls(){
        txtAmount.setText("");
        txtRoomNo.setText("");
        txtDate.setText("");
        txtTime.setText("");
    }


}