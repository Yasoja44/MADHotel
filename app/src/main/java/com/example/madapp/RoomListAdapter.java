package com.example.madapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RoomListAdapter extends ArrayAdapter <Room> {

    Context mCtx;
    int resource;
    List<Room> roomList;

    public RoomListAdapter(Context mCtx, int resource, List<Room> roomList) {
        super(mCtx, resource,roomList);

        this.mCtx = mCtx;
        this.resource = resource;
        this.roomList = roomList;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(resource,null);

        final TextView roomNo = view.findViewById(R.id.lb_roomNo4);
        final TextView rType = view.findViewById(R.id.lb_Rtype);
        TextView price = view.findViewById(R.id.lb_price);
        TextView f1 = view.findViewById(R.id.lb_f1);
        TextView f2 = view.findViewById(R.id.lb_f2);
        TextView f3 = view.findViewById(R.id.lb_f3);
        TextView f4 = view.findViewById(R.id.lb_f4);
        ImageView image = view.findViewById(R.id.imageView2);

       Button btnBook = view.findViewById(R.id.btn_booking);

       btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mCtx, AddBookingDetails.class);
                intent.putExtra("roomNo",roomNo.getText().toString());
                intent.putExtra("RType",rType.getText().toString());
                mCtx.startActivity(intent);

            }
        });

        Room rl = roomList.get(position);

        roomNo.setText(rl.getRno());
        rType.setText(rl.getrType());
        price.setText(rl.getPrice());
        f1.setText(rl.getF1());
        f2.setText(rl.getF2());
        f3.setText(rl.getF3());
        f4.setText(rl.getF4());
        image.setImageDrawable(mCtx.getResources().getDrawable(rl.getImage()));


        return view;
    }

}
