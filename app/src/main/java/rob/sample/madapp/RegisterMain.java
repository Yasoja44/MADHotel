package rob.sample.madapp;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterMain extends AppCompatActivity {

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerPage(android.view.View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Register.class));
        finish();
    }

    public void loginPage(android.view.View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    public void feedbackPage(android.view.View view) {
        FirebaseAuth.getInstance();
        startActivity(new Intent(getApplicationContext(),feedbackUser.class));
        finish();
    }

    public void profilePage(android.view.View view) {
        FirebaseAuth.getInstance();
        startActivity(new Intent(getApplicationContext(),profile.class));
        finish();
    }
}