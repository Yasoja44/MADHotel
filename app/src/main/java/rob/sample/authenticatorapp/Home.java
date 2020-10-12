package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnFood,btnBook,btnOther;
    Button btnAbout,btnLogin,btnFeed,btnFood2,btnBook2,btnOther2;
    Intent intent;
    String USER_ID2;
    public static final String USER_ID = "U01";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);


        btnFood = findViewById(R.id.imgbtn_2newHomeRoom);
        btnBook = findViewById(R.id.imgbtn_2newHomeBooking);
        btnOther = findViewById(R.id.imgbtn_2newHomeOther);
        btnFood2 = findViewById(R.id.btn_2newHomeBooking);
        btnBook2 = findViewById(R.id.btn_2newHomeRoom);
        btnOther2 = findViewById(R.id.btn_2newHomeOther);
        btnFeed = findViewById(R.id.btn_2HomeFeedback2);
        btnAbout = findViewById(R.id.btn_2HomeAbout2);
        btnLogin = findViewById(R.id.btn_2HomeLogin2);

        btnFood.setOnClickListener(this);
        btnBook.setOnClickListener(this);
        btnOther.setOnClickListener(this);
        btnFeed.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnFood2.setOnClickListener(this);
        btnBook2.setOnClickListener(this);
        btnOther2.setOnClickListener(this);


    }

    protected void onResume() {
        super.onResume();
        btnFood.setOnClickListener(this);
        btnBook.setOnClickListener(this);
        btnOther.setOnClickListener(this);
        btnFeed.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnFood2.setOnClickListener(this);
        btnBook2.setOnClickListener(this);
        btnOther2.setOnClickListener(this);
    }

        @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_2newHomeRoom: Food();
                break;
            case R.id.imgbtn_2newHomeBooking: Book();
                break;
            case R.id.imgbtn_2newHomeOther: Other();
                break;
            case R.id.btn_2HomeFeedback2: Feed();
                break;
            case R.id.btn_2HomeAbout2: About();
                break;
            case R.id.btn_2HomeLogin2: Login();
                break;
            case R.id.btn_2newHomeBooking: Food();
                break;
            case R.id.btn_2newHomeRoom: Book();
                break;
            case R.id.btn_2newHomeOther: Other();
                break;
        }
    }

    public void Food(){

        Toast.makeText(getApplicationContext(), "Login First", Toast.LENGTH_SHORT).show();


    }
    public void Book(){
        Toast.makeText(getApplicationContext(), "Login First", Toast.LENGTH_SHORT).show();
    }
    public void Other(){
        Toast.makeText(getApplicationContext(), "Login First", Toast.LENGTH_SHORT).show();
    }
    public void Feed(){
        Toast.makeText(getApplicationContext(), "Login First", Toast.LENGTH_SHORT).show();
    }
    public void About(){
        Toast.makeText(getApplicationContext(), "Login First", Toast.LENGTH_SHORT).show();
    }
    public void Login(){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

}