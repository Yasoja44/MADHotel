package rob.sample.madapp;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mPhone, mconfirmpassword;
    android.widget.TextView mloginText;
    Button mRegister;
    FirebaseAuth fAuth;
    ProgressBar mprogressBar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mconfirmpassword = findViewById(R.id.confirmpassword);
        mPhone = findViewById(R.id.phone);
        mRegister = findViewById(R.id.register);
        mloginText = findViewById(R.id.loginText);

        databaseReference = FirebaseDatabase.getInstance().getReference("registerClass");
        fAuth = FirebaseAuth.getInstance();

        fAuth = FirebaseAuth.getInstance();
        mprogressBar = findViewById(R.id.progressBar);

    /*  if (fAuth.getCurrentUser() != null) {

            startActivity(new Intent(getApplicationContext(), RegisterMain.class));
            finish();
        }*/

        mRegister.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                final String fullname = mFullName.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmpassword = mconfirmpassword.getText().toString().trim();

                final String cfullName = mFullName.getText().toString().trim();
                final String cEmail = mEmail.getText().toString().trim();
                final String cPhone = mPhone.getText().toString().trim();

                if (TextUtils.isEmpty(fullname)) {

                    mFullName.setError("Full Name is Require");


                } else if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Email is Require");

                } else if (TextUtils.isEmpty(phone)) {

                    mPhone.setError("Phone Number is Require");

                } else if (TextUtils.isEmpty(password)) {

                    mPassword.setError("Password is Require");

                } else if (password.length() < 6) {

                    mPassword.setError("Password require 6 digits");

                } else if (!password.equals(confirmpassword)) {

                    mconfirmpassword.setError("Password Not matching");

                } else {

                mprogressBar.setVisibility(android.view.View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<AuthResult> task) {


                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(Register.this, "Invalid Email Address",Toast.LENGTH_LONG).show();
                            mprogressBar.setVisibility(android.view.View.GONE);

                        } else if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Error! "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            mprogressBar.setVisibility(android.view.View.GONE);

                        } else {
                            Toast.makeText(Register.this, "Register Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));

                            registerClass information = new registerClass(
                                    cfullName,
                                    cEmail,
                                    cPhone
                            );
                            FirebaseDatabase.getInstance().getReference("registerClass")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                }
                            });
                        }
                    }
                });

            }

            }

        });

        mloginText.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }

        });

        }
 }