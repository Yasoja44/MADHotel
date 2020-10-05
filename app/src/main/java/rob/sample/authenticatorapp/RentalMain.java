package rob.sample.authenticatorapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RentalMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {


    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    EditText txtVno,txtName,txtDes,txtCon,txtEmail;
    Spinner spin;
    Button btnAdd,btnDelete,btnEdit,addcam,addgall,btnShow;
    DatabaseReference dbRef,dbRef2;
    ManageRental mrental;
    Map<String,Object> updateMap;
    ImageView imageV;
    String currentPhotoPath;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vehicles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spin = (Spinner) findViewById(R.id.spinner1);
        txtVno = (EditText) findViewById(R.id.txtRentalVNo);
        txtName = (EditText) findViewById(R.id.txtRentalName);
        txtDes = (EditText) findViewById(R.id.txtRentalDes);
        txtCon = (EditText) findViewById(R.id.txtRentalCon);
        txtEmail = (EditText) findViewById(R.id.txtRentalEmail);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnShow = (Button) findViewById(R.id.btnShow);
        addcam = (Button) findViewById(R.id.btnCam);
        addgall = (Button) findViewById(R.id.btnGall);

        imageV = findViewById(R.id.imgView);

        storageReference = FirebaseStorage.getInstance().getReference();


        mrental = new ManageRental();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();


            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteData();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowData();
            }
        });

        addcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
                //Toast.makeText(MainActivity.this,"Camera Button is Clicked",Toast.LENGTH_LONG).show();

            }
        });
        addgall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addgall = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(addgall, RentalMain.GALLERY_REQUEST_CODE);

            }
        });

        addgall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });
        // Intent intent = new Intent(this,.class);
        // startActivity(intent);

     //Intent intent = new Intent(this,ManageGuideDetails.class);
        //startActivity(intent);

    }
    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else{
                Toast.makeText(RentalMain.this,"Camera Permission is Required to Use Camera",Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                File f =new File(currentPhotoPath);
                // selectedImage.setImageURI(Uri.fromFile(f));
                Log.d("tag","Absolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                uploadImageToFirebase(f.getName(),contentUri);
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "."+getFileExt(contentUri);
                Log.d("tag","onActivityResult: Gallery Image Uri:  " + imageFileName);
                //selectedImage.setImageURI(contentUri);


                uploadImageToFirebase(imageFileName,contentUri);
            }
        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = storageReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageV);
                    }
                });
                Toast.makeText(RentalMain.this,"Image is Uploaded",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RentalMain.this,"Upload Faild",Toast.LENGTH_LONG).show();
            }
        });
    }


    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }






    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void UpdateData(){

        dbRef = FirebaseDatabase.getInstance().getReference();
        Query updateQuery = dbRef.child("ManageRental").orderByChild("vNo").equalTo(txtVno.getText().toString().trim());
        updateMap = new HashMap<String,Object>();
        updateMap.put("vType",spin.getSelectedItem().toString().trim());
        updateMap.put("vNo",txtVno.getText().toString().trim());
        updateMap.put("oName",txtName.getText().toString().trim());
        updateMap.put("description",txtDes.getText().toString().trim());
        updateMap.put("contact",txtCon.getText().toString().trim());
        updateMap.put("email",txtEmail.getText().toString().trim());

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

    private void DeleteData(){

        dbRef2 = FirebaseDatabase.getInstance().getReference();
        Query deleteQuery = dbRef2.child("ManageRental").orderByChild("vNo").equalTo(txtVno.getText().toString().trim());
        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                    deleteSnapshot.getRef().removeValue();

                    Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                    clearControls();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void ShowData(){
        Intent intent = new Intent(this,CustomRental.class);
        startActivity(intent);
    }

    private void SaveData(){
        dbRef = FirebaseDatabase.getInstance().getReference().child("ManageRental");
        try{
            if(TextUtils.isEmpty(txtVno.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Vehicle no",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtName.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Owner Name",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtDes.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Description",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtCon.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Contact No",Toast.LENGTH_SHORT).show();
            else if(!(txtCon.getText().toString().length() == 10))
                Toast.makeText(getApplicationContext(),"Invalid Contact No",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtEmail.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Email Address",Toast.LENGTH_SHORT).show();
            else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches())
                Toast.makeText(getApplicationContext(),"Invalid Email Address",Toast.LENGTH_SHORT).show();
            else{



                mrental.setvType(spin.getSelectedItem().toString().trim());
                mrental.setvNo(txtVno.getText().toString().trim());
                mrental.setoName(txtName.getText().toString().trim());
                mrental.setDescription(txtDes.getText().toString().trim());
                mrental.setContact(Integer.parseInt(txtCon.getText().toString().trim()));
                mrental.setEmail(txtEmail.getText().toString().trim());

                dbRef.push().setValue(mrental);


                Toast.makeText(getApplicationContext(),"Successfully Inserted",Toast.LENGTH_SHORT).show();
                clearControls();
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText(getApplicationContext(),"Invalid Contact No",Toast.LENGTH_SHORT).show();
        }
    }



    private void clearControls(){
        txtVno.setText("");
        txtName.setText("");
        txtDes.setText("");
        txtName.setText("");
        txtCon.setText("");
        txtEmail.setText("");
        Intent intent = new Intent(this,CustomRental.class);
        startActivity(intent);
    }
}