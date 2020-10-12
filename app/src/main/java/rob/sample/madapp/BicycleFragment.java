package rob.sample.madapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link BicycleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BicycleFragment extends androidx.fragment.app.Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "Bicycle";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    DatabaseReference dbRef;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BicycleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BicycleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BicycleFragment newInstance(String param1, String param2) {
        BicycleFragment fragment = new BicycleFragment();
        android.os.Bundle args = new android.os.Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        initImageBitmaps();
    }

    private void initImageBitmaps() {

        dbRef = FirebaseDatabase.getInstance().getReference();
        Query showQuery = dbRef.child("ManageRental").orderByChild("vType").equalTo("Bicycle".trim());

        showQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot showSnapshot: dataSnapshot.getChildren()) {
                    android.util.Log.d(TAG, "initImageBitmaps: started");
                    mImageUrls.add("https://cdn.shopify.com/s/files/1/1245/1481/products/Classic_Plus_Side_Profile_web.jpg?v=1597774901");
                    mNames.add("Vehicle Type: "+showSnapshot.child("vType").getValue().toString() + "\n"+
                            "Vehicle No: "+showSnapshot.child("vNo").getValue().toString() + "\n"+
                            "Owner Name: "+showSnapshot.child("oName").getValue().toString() + "\n" +
                            "Description: " + showSnapshot.child("description").getValue().toString() + "\n" +
                            "Contact No: " + showSnapshot.child("contact").getValue().toString() + "\n" +
                            "E-mail: " + showSnapshot.child("email").getValue().toString() + "\n" + "\n\n");
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
        RecyclerView recyclerView = getView().findViewById(R.id.vehi_recycler_view);
        JanithRecyclerViewAdapter adapter = new JanithRecyclerViewAdapter(mNames,mImageUrls,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          android.os.Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bicycle, container, false);
    }
}