package rob.sample.madapp;

import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowPage extends AppCompatActivity implements android.view.View.OnClickListener{

    //TextView txtFeed;
    Button btnshow;
    String count,iString,itemp;
    int countInt;
    DatabaseReference dbRef2;

    private static final String TAG = "FeedbackShowNew";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_page);

        //txtFeed = findViewById(R.id.feedback_recycler_view);
        //btnshow = findViewById(R.id.btnShow);

        //btnshow.setOnClickListener(this);

        initImageBitmaps();

        //Intent intent = getIntent();
        //count = intent.getStringExtra(feedbackUser.EXTRA_MESSAGE);
        //assert count != null;
        //countInt = Integer.parseInt(count);

    }

    private void initImageBitmaps() {
        android.util.Log.d(TAG, "onCreate: started");
        dbRef2 = FirebaseDatabase.getInstance().getReference();
        Query showQuery = dbRef2.child("feedbackclass").orderByChild("name");

        showQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot showSnapshot: dataSnapshot.getChildren()) {
                    android.util.Log.d(TAG, "initImageBitmaps: started");
                    mImageUrls.add("https://image.shutterstock.com/image-vector/feedback-chart-keywords-icons-sketch-260nw-374733526.jpg");
                    mNames.add(showSnapshot.child("name").getValue().toString() + "\n"+ Objects.requireNonNull(showSnapshot.child("email").getValue()).toString() + "\n"
                                        + Objects.requireNonNull(showSnapshot.child("feedback").getValue()).toString()+ "\n\n");
                    initRecyclerView();
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView() {
        android.util.Log.d(TAG, "initRecyclerView: started");
        RecyclerView recyclerView = findViewById(R.id.feedback_recycler_view);
        IsuruRecyclerViewAdapter adapter = new IsuruRecyclerViewAdapter(mNames,mImageUrls,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onClick(android.view.View view) {
        if (view.getId() == R.id.btnShow) {
            Show();
        }
    }

    public void Show(){

        //for(int i = 1; i <= countInt ; i++){
        //    iString = String.valueOf(i);
         //   itemp = "fb" + iString;
         //   dbRef2 = FirebaseDatabase.getInstance().getReference().child("feedbackclass").child(itemp);
         //   dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {

         //       @Override
          //      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
          //          if(dataSnapshot.hasChildren()){
           //             txtShow.append(Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString() + "\n"+ Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString() + "\n"
           //                     + Objects.requireNonNull(dataSnapshot.child("feedback").getValue()).toString());
            //        }
            //    }

             //   @Override
            //    public void onCancelled(@NonNull DatabaseError databaseError) {
//
            //    }
           // });



    }

}