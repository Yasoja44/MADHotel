package rob.sample.madapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    android.widget.TextView mregisterText, mforgotpassword;
    Button mLogin;
    FirebaseAuth fAuth;
    ProgressBar mprogressBar;
    public static final String UID_EXTRA = "";

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);
        mregisterText = findViewById(R.id.registerText);
        mforgotpassword = findViewById(R.id.forgotpassword);

        fAuth = FirebaseAuth.getInstance();
        mprogressBar = findViewById(R.id.progressBarLogin);

        mLogin.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Email is Require");
                    return;

                } else if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }

                mprogressBar.setVisibility(android.view.View.VISIBLE);
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getApplicationContext(), Home2.class));
                            Intent intent = new Intent(getApplicationContext(),Home2.class);
                            intent.putExtra(UID_EXTRA,email);
                            startActivity(intent);
                        } else {

                            Toast.makeText(Login.this, "Email or Password is Invalid! ", Toast.LENGTH_LONG).show();
                            mprogressBar.setVisibility(android.view.View.GONE);

                        }
                    }

                });
            }
        });

        mregisterText.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                //startActivity(new Intent(getApplicationContext(),Register.class));

                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

        mforgotpassword.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {

                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset password?");
                passwordResetDialog.setMessage("Enter Your Email to Received the link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //send link to the email

                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(Login.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@androidx.annotation.NonNull Exception e) {
                                Toast.makeText(Login.this, "Reset Link Not Sent. "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //close the dialog

                    }
                });
                passwordResetDialog.create().show();

            }
        });

    }
}