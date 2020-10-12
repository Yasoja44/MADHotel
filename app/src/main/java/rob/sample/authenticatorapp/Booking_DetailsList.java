package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Booking_DetailsList extends AppCompatActivity {
    //we will use these constants later to pass the artist name and id to another activity
    public static final String BOOKING_ID = "net.simplifiedcoding.firebasedatabaseexample.artistname";
    public static final String ROOM_NO = "net.simplifiedcoding.firebasedatabaseexample.artistid";

    //view objects
    EditText editTextName;
    Spinner spinnerGenre;
    Button buttonAddArtist;
    ListView listViewBooking;

    //a list to store all the artist from firebase database
    List<BookRoom> BRoom;

    //our database reference object
    DatabaseReference databaseBRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        final loadingDialog load = new loadingDialog(Booking_DetailsList.this);
        load.startLoadingDailog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                load.dismissDailog();
            }
        },3000);
        //getting the reference of artists node


        databaseBRoom = FirebaseDatabase.getInstance().getReference("Booking");



        //getting views
       // editTextName = (EditText) findViewById(R.id.editTextName);
        //spinnerGenre = (Spinner) findViewById(R.id.spinnerGenres);
        listViewBooking = (ListView) findViewById(R.id.listViewArtists);

        //buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);

        //list to store artists
        BRoom = new ArrayList<>();


        //adding an onclicklistener to button
      /*  buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addArtist();
            }
        });*/

        listViewBooking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookRoom BRooms = BRoom.get(i);
                showUpdateDeleteDialog(BRooms.getBookingId(), BRooms.getRoomNo(), BRooms.getAdultNo(), BRooms.getChildNo(), BRooms.getCheckIn(), BRooms.getCheckOut());

            }
        });

     /*  listViewBooking.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                //getting the selected artist
                BookRoom BRooms = BRoom.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);

                //putting artist name and id to intent
                intent.putExtra(BOOKING_ID, BRooms.getBookingId());
                intent.putExtra(ROOM_NO, BRooms.getRoomNo());

                //starting the activity with intent
                startActivity(intent);
                return false;
            }
        });*/
    }

    /*
     * This method is saving a new artist to the
     * Firebase Realtime Database
     * */
  /*  private void addArtist() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseArtists.push().getKey();

            //creating an Artist Object
            Artist artist = new Artist(id, name, genre);

            //Saving the Artist
            databaseArtists.child(id).setValue(artist);

            //setting edittext to blank again
            editTextName.setText("");

            //displaying a success toast
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }*/


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseBRoom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                BRoom.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    String desk = getIntent().getStringExtra("ID");
                    String userId = postSnapshot.child("userId").getValue(String.class);
                    if(desk.equals(userId)){



                    //getting artist
                    BookRoom Broom = postSnapshot.getValue(BookRoom.class);
                    //adding artist to the list
                    BRoom.add(Broom);}
                }

                //creating adapter
                Booked_DetailsList BookingAdapter = new Booked_DetailsList(Booking_DetailsList.this, BRoom);
                //attaching adapter to the listview
                listViewBooking.setAdapter(BookingAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

   private void showUpdateDeleteDialog(final String bookingId, final String roomNo, final String adultNo, final String childNo, final String checkIn, final String checkOut) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);


        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

       // dialogBuilder.setTitle(bookingId);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Booking_DetailsList.this, EditBookingDetails.class);
                intent.putExtra("Bookingid",bookingId);
                intent.putExtra("Roomno",roomNo);
                intent.putExtra("Adultno",adultNo);
                intent.putExtra("Childno",childNo);
                intent.putExtra("Checkin",checkIn);
                intent.putExtra("Checkout",checkOut);
                intent.putExtra("IDP",getIntent().getStringExtra("ID"));
                startActivity(intent);


            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(bookingId);
                b.dismiss();
            }
        });
    }


  /*  private boolean updateArtist(String id, String roomNo,String adultNo,String childNo,String checkIn,String checkOut) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("BookRoom").child(id);

        //updating artist
        BookRoom BRooms = new BookRoom(id, roomNo, adultNo,childNo,checkIn,checkOut);
        dR.setValue(BRooms);
        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }*/


    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Booking").child(id);

        //removing artist
        dR.removeValue();

        //getting the tracks reference for the specified artist
      /*  DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        //removing all tracks
        drTracks.removeValue();*/
        Toast.makeText(getApplicationContext(), "Booking Cancel", Toast.LENGTH_LONG).show();

        return true;
    }
}
