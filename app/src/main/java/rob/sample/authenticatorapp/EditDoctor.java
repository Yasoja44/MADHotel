package rob.sample.authenticatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EditDoctor extends AppCompatActivity implements View.OnClickListener{

    EditText txtdid, txtdname, txtdspecilaity, txtdaddress, txtdphone, txtdemail;
    Button btnEdit,btnDelete;
    String count;
    int countInt;
    DatabaseReference dbRef,dbRef2;
    Map<String,Object> updateMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor);

        txtdid = findViewById(R.id.txt_did2);
        txtdname = findViewById(R.id.txt_dname2);
        txtdspecilaity = findViewById(R.id.txt_dSpeciality2);
        txtdaddress = findViewById(R.id.txt_daddress2);
        txtdphone = findViewById(R.id.txt_dphone2);
        txtdemail = findViewById(R.id.txt_demail2);

        btnEdit = findViewById(R.id.btn_dedit2);
        btnDelete = findViewById(R.id.btn_dDelete2);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    protected void onResume() {
        super.onResume();
        txtdid = findViewById(R.id.txt_did2);
        txtdname = findViewById(R.id.txt_dname2);
        txtdspecilaity = findViewById(R.id.txt_dSpeciality2);
        txtdaddress = findViewById(R.id.txt_daddress2);
        txtdphone = findViewById(R.id.txt_dphone2);
        txtdemail = findViewById(R.id.txt_demail2);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dedit2:Edit();
                break;
            case R.id.btn_dDelete2:Delete();
                break;
        }
    }

    public void Delete(){

        dbRef2 = FirebaseDatabase.getInstance().getReference();
        Query deleteQuery = dbRef2.child("Doctor").orderByChild("doctorID").equalTo(Integer.parseInt(txtdid.getText().toString().trim()));

        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                    deleteSnapshot.getRef().removeValue();
                    Toast.makeText(getApplicationContext(), "Successfully Removed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Edit(){
        dbRef = FirebaseDatabase.getInstance().getReference();
        Query updateQuery = dbRef.child("Doctor").orderByChild("doctorID").equalTo(Integer.parseInt(txtdid.getText().toString().trim()));

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
        }else {

            updateMap = new HashMap<String,Object>();
            updateMap.put("doctorID",txtdid.getText().toString().trim());
            updateMap.put("doctorName",txtdname.getText().toString().trim());
            updateMap.put("doctorSpeciality",txtdspecilaity.getText().toString().trim());
            updateMap.put("doctorAddress",txtdaddress.getText().toString().trim());
            updateMap.put("doctorPhone",txtdphone.getText().toString().trim());
            updateMap.put("doctorEmail",txtdemail.getText().toString().trim());

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

    private void clearControls(){
        txtdid.setText("");
        txtdname.setText("");
        txtdspecilaity.setText("");
        txtdaddress.setText("");
        txtdphone.setText("");
        txtdemail.setText("");
    }
}