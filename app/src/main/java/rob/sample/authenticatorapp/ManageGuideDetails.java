package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ManageGuideDetails extends AppCompatActivity implements View.OnClickListener {


    EditText txtgArea,txtgName,txtgDes,txtgCon,txtgEmail;
    Button btnAdd,btnDelete,btnEdit,btnShow;
    DatabaseReference dbRef,dbRef2;
    ManageGuide mguide;
    Map<String,Object> updateMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_guide_details);

        Intent intent = getIntent();

        txtgArea = (EditText)findViewById(R.id.txtGuideArea);
        txtgName = (EditText)findViewById(R.id.txtGuideName);
        txtgDes = (EditText)findViewById(R.id.txtGuideDes);
        txtgCon = (EditText)findViewById(R.id.txtGuideCon);
        txtgEmail = (EditText)findViewById(R.id.txtGuideEmail);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnShow = (Button)findViewById(R.id.btnShow);

        mguide = new ManageGuide();

        btnAdd.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:Savedata();
                break;
            case R.id.btnShow:Show();
                break;
            case R.id.btnDelete:Deletedata();
                break;
            case R.id.btnEdit:Updatedata();
              break;
        }
    }


    public void Deletedata(){




        dbRef2 = FirebaseDatabase.getInstance().getReference();
        Query deleteQuery = dbRef2.child("ManageGuide").orderByChild("gemail").equalTo(txtgEmail.getText().toString().trim());
        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                    deleteSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void Updatedata(){



        dbRef2 = FirebaseDatabase.getInstance().getReference();
        Query updateQuery = dbRef2.child("ManageGuide").orderByChild("gemail").equalTo(txtgEmail.getText().toString().trim());
        updateMap = new HashMap<String,Object>();

        /*updateMap.put("garea",txtgArea.getText().toString().trim());
        updateMap.put("gname",txtgName.getText().toString().trim());
        updateMap.put("gdes",txtgDes.getText().toString().trim());
        updateMap.put("gcon",txtgCon.getText().toString().trim());
        updateMap.put("gemail",txtgEmail.getText().toString().trim());*/

        if(!TextUtils.isEmpty(txtgArea.getText().toString())){
            updateMap.put("garea", txtgArea.getText().toString().trim());
        }else if(!TextUtils.isEmpty(txtgName.getText().toString())) {
            updateMap.put("gname", txtgName.getText().toString().trim());
        }else if(!TextUtils.isEmpty(txtgDes.getText().toString())) {
            updateMap.put("gdes", txtgDes.getText().toString().trim());
        }else if(!TextUtils.isEmpty(txtgCon.getText().toString())) {
            updateMap.put("gcon", txtgCon.getText().toString().trim());
        }


        updateQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot updateSnapshot: dataSnapshot.getChildren()) {


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

    public void Show(){

        Intent intent = new Intent(this,ShowGuide.class);
        //intent.putExtra(EXTRA_MESSAGE, String.valueOf(count));
        startActivity(intent);


    }

    public void Savedata(){
        dbRef = FirebaseDatabase.getInstance().getReference().child("ManageGuide");
        try{
            if(TextUtils.isEmpty(txtgArea.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Guide Area",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtgName.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Guider's Name",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtgDes.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Description",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtgCon.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Contact No",Toast.LENGTH_SHORT).show();
            else if(!(txtgCon.getText().toString().length() == 10))
                Toast.makeText(getApplicationContext(),"Invalid Contact No",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtgEmail.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Email Address",Toast.LENGTH_SHORT).show();
            else if(!Patterns.EMAIL_ADDRESS.matcher(txtgEmail.getText().toString()).matches())
                Toast.makeText(getApplicationContext(),"Invalid Email Address",Toast.LENGTH_SHORT).show();
            else{

                Boolean yes = checkDes(txtgDes.getText().toString());

                if(yes == true) {


                    mguide.setGArea(txtgArea.getText().toString().trim());
                    mguide.setGName(txtgName.getText().toString().trim());
                    mguide.setGDes(txtgDes.getText().toString().trim());
                    mguide.setGCon(Integer.parseInt(txtgCon.getText().toString().trim()));
                    mguide.setGEmail(txtgEmail.getText().toString().trim());
                    dbRef.push().setValue(mguide);


                    Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_SHORT).show();
                    clearControls();
                }else{
                    Toast.makeText(getApplicationContext(), "Description too short", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText(getApplicationContext(),"Invalid Contact No",Toast.LENGTH_SHORT).show();
        }
    }
    public Boolean checkDes(String des){
        int count  = des.length();
        if(count < 5){
            return false;
        }else{
            return true;
        }
    }

    private void clearControls(){
        txtgArea.setText("");
        txtgName.setText("");
        txtgDes.setText("");
        txtgCon.setText("");
        txtgEmail.setText("");
    }


}