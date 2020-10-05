package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rob.sample.authenticatorapp.Database.DoctorsMaster;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class AddDoctor extends AppCompatActivity implements View.OnClickListener {

    EditText txtdid, txtdname, txtdspecilaity, txtdaddress, txtdphone, txtdemail;
    Button btnAdd,btnShow;
    DatabaseReference dbRef;
    DoctorsMaster doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        txtdid = findViewById(R.id.txt_dId);
        txtdname = findViewById(R.id.txt_dName);
        txtdspecilaity = findViewById(R.id.txt_DSpeciality);
        txtdaddress = findViewById(R.id.txt_dAddress);
        txtdphone = findViewById(R.id.txt_dPhone);
        txtdemail = findViewById(R.id.txt_dEmail);

        btnAdd = findViewById(R.id.btn_dAdd);
        btnShow = findViewById(R.id.btn_dShow);

        doc = new DoctorsMaster();

        btnAdd.setOnClickListener(this);
        btnShow.setOnClickListener(this);

    }
    protected void onResume() {
        super.onResume();
        txtdid = findViewById(R.id.txt_dId);
        txtdname = findViewById(R.id.txt_dName);
        txtdspecilaity = findViewById(R.id.txt_DSpeciality);
        txtdaddress = findViewById(R.id.txt_dAddress);
        txtdphone = findViewById(R.id.txt_dPhone);
        txtdemail = findViewById(R.id.txt_dEmail);
        btnAdd.setOnClickListener(this);
        btnShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dAdd:Add();
                break;
            case R.id.btn_dShow:Show();
                break;
        }
    }
    public void Add(){
        dbRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
        try {

            if (TextUtils.isEmpty(txtdname.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Name", Toast.LENGTH_SHORT).show();
            if (TextUtils.isEmpty(txtdid.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty ID", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtdspecilaity.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Speciality", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtdaddress.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Address", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtdphone.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Contact No", Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(txtdemail.getText().toString()))
                Toast.makeText(getApplicationContext(), "Empty Email", Toast.LENGTH_SHORT).show();
            else {
                doc.setDoctorID(Integer.parseInt(txtdid.getText().toString().trim()));
                doc.setDoctorName(txtdname.getText().toString().trim());
                doc.setDoctorSpeciality(txtdspecilaity.getText().toString().trim());
                doc.setDoctorAddress(txtdaddress.getText().toString().trim());
                doc.setDoctorPhone(Integer.parseInt(txtdphone.getText().toString().trim()));
                doc.setDoctorEmail(txtdemail.getText().toString().trim());

                String phoneTemp = txtdphone.getText().toString();
                String emailTemp = txtdemail.getText().toString();
                int count = 0;
                for(int i = 0 ; i < emailTemp.length() ; i++){
                    String atTemp = String.valueOf(emailTemp.charAt(i));
                    if(atTemp.equals("@")){
                        count += 1;
                    }
                }
                if(phoneTemp.length() != 10){
                    Toast.makeText(getApplicationContext(), "Need 10 digits", Toast.LENGTH_SHORT).show();
                }else if(count != 1){
                    Toast.makeText(getApplicationContext(), "Incorrect Email Address", Toast.LENGTH_SHORT).show();
                }else{
                    dbRef.push().setValue(doc);
                    Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
                    clearControls();
                }

            }
        } catch (NumberFormatException nfe) {
            Toast.makeText(getApplicationContext(), "Invalid Contact No or ID", Toast.LENGTH_SHORT).show();
        }
    }

    public void Show(){
        Intent intent = new Intent(this,DoctorShowNew.class);
        startActivity(intent);
    }

    private void clearControls(){
        txtdid.setText("");
        txtdname.setText("");
        txtdspecilaity.setText("");
        txtdaddress.setText("");
        txtdphone.setText("");
        txtdemail.setText("");
    }

}