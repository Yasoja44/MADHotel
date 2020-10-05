package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ServicesShow2 extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnVehicle,btnDocotr,btnGuide,btnLogin;
    Button btnAbout,btnFeed,btnVehicle2,btnDocotr2,btnGuide2;
    Intent intentGet,intent;
    String UID;

    public static String USER_ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_show2);

        btnVehicle = findViewById(R.id.imgbtn_newServiceVehi);
        btnDocotr = findViewById(R.id.imgbtn_newServiceDoc);
        btnGuide = findViewById(R.id.imgbtn_newServiceGuide);
        btnFeed = findViewById(R.id.btn_NewServiceFeedback2);
        btnAbout = findViewById(R.id.btn_NewServiceAbout2);
        btnLogin = findViewById(R.id.btn_profileService);
        btnVehicle2 = findViewById(R.id.btn_newServiceVehi);
        btnDocotr2 = findViewById(R.id.btn_newServiceDoc);
        btnGuide2 = findViewById(R.id.btn_newServiceGuide);

        btnVehicle.setOnClickListener(this);
        btnDocotr.setOnClickListener(this);
        btnGuide.setOnClickListener(this);
        btnFeed.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnVehicle2.setOnClickListener(this);
        btnDocotr2.setOnClickListener(this);
        btnGuide2.setOnClickListener(this);

        intentGet = getIntent();
        UID = intentGet.getStringExtra(Home2.USER_ID);
    }

    protected void onResume() {
        super.onResume();
        btnVehicle.setOnClickListener(this);
        btnDocotr.setOnClickListener(this);
        btnGuide.setOnClickListener(this);
        btnFeed.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnVehicle2.setOnClickListener(this);
        btnDocotr2.setOnClickListener(this);
        btnGuide2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_newServiceVehi: Vehicle();
                break;
            case R.id.imgbtn_newServiceDoc: Doctor();
                break;
            case R.id.imgbtn_newServiceGuide: Guide();
                break;
            case R.id.btn_NewServiceFeedback2: Feed();
                break;
            case R.id.btn_NewServiceAbout2: About();
                break;
            case R.id.btn_profileService: Profile();
                break;
            case R.id.btn_newServiceVehi: Vehicle();
                break;
            case R.id.btn_newServiceDoc: Doctor();
                break;
            case R.id.btn_newServiceGuide: Guide();
                break;
        }
    }

    public void Feed(){
        intent = new Intent(this,feedbackUser.class);
        //intent.putExtra(USER_ID,UID);
        startActivity(intent);
    }
    public void About(){

    }
    public void Profile(){
        if(UID.equals("admin@gmail.com")){
            Toast.makeText(getApplicationContext(), "You are Loging as", Toast.LENGTH_SHORT).show();

        }
        else{
            intent = new Intent(this,profile.class);
            intent.putExtra(USER_ID,UID);
            startActivity(intent);
        }

    }
    public void Guide(){
        if(UID.equals("admin@gmail.com")) {
            intent = new Intent(this, ManageGuideDetails.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this,ShowGuide.class);
            startActivity(intent);
        }
    }
    public void Doctor(){
        if(UID.equals("admin@gmail.com")){
            intent = new Intent(this,AddDoctor.class);
            startActivity(intent);
        }else{
            intent = new Intent(this,DoctorShow.class);
            startActivity(intent);
        }
    }

    public void Vehicle(){
        if(UID.equals("admin@gmail.com")){
            intent = new Intent(this,RentalMain.class);
            startActivity(intent);
        }else{
            intent = new Intent(this,CustomRental.class);
            startActivity(intent);
        }
    }
}