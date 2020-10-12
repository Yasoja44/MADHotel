package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class RoomList extends AppCompatActivity {

    ListView listView;
    List<Room> RoomList;

    DatabaseReference dbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        Intent intent = getIntent();
        String UID = intent.getStringExtra(Home2.USER_ID);

        listView = (ListView) findViewById(R.id.listViewRoom);
        RoomList = new ArrayList<>();


        RoomList.add(new Room(R.drawable.room01,"Non-AC","LCD-TV","A bedside table","Rain shower"," Per Day: LKR 1000.00","Budget Room","01",UID));
        RoomList.add(new Room(R.drawable.room5,"Non-AC","Free Bed-Tea","IDD Call","Rain shower"," Per Day: LKR 1200.00","Single Room","02",UID));
        RoomList.add(new Room(R.drawable.room4,"AC","LED-TV","Sitting Area","Rain shower"," Per Day: LKR 2500.00","Double Room","03",UID));
        RoomList.add(new Room(R.drawable.room6,"AC","72-Inch Tv","Refrigerator","Bath tub"," Per Day: LKR 5000.00","Luxury Room","04",UID));
        RoomList.add(new Room(R.drawable.room7,"Non-AC","Free Bed-Tea","Sitting Area","Rain shower"," Per Day: LKR 3500.00","Deluxe Room","05",UID));

        listView = (ListView) findViewById(R.id.listViewRoom);

        RoomListAdapter adapter = new RoomListAdapter(this, R.layout.activity_room_details_list,RoomList);
        listView.setAdapter(adapter);

       /* dbref = FirebaseDatabase.getInstance().getReference("BookRoom");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RoomList.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    BookRoom bookRoom = dataSnapshot1.getValue(BookRoom.class);
                    RoomList.add(bookRoom);


                }

              RoomListAdapter adapter = new RoomListAdapter(RoomList.this,R.layout.bookig_details_list,RoomList);

               // ListAdapter adapter = new ListAdapter(RoomList.this, bookRoomList);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
}