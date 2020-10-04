package com.example.madapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Belal on 2/26/2017.
 */

public class AdminBooked_DetailsList extends ArrayAdapter<BookRoom> {
    private Activity context;
    List<BookRoom> BRoom;

    public AdminBooked_DetailsList(Activity context, List<BookRoom> BRoom) {
        super(context, R.layout.layout_admin_booking_details_list, BRoom);
        this.context = context;
        this.BRoom = BRoom;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_admin_booking_details_list, null, true);

     //   TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
       // TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        TextView roomNo = listViewItem.findViewById(R.id.lb_roomNo);
        TextView adultNo = listViewItem.findViewById(R.id.lb_adultNo2);
        TextView childNo = listViewItem.findViewById(R.id.lb_childNo2);
        TextView checkIn = listViewItem.findViewById(R.id.lb_checkIn2);
        TextView childOut = listViewItem.findViewById(R.id.lb_checkOut2);
//        TextView id = listViewItem.findViewById(R.id.textViewName);

        BookRoom BRooms = BRoom.get(position);

        roomNo.setText(BRooms.getRoomNo());
        adultNo.setText(BRooms.getAdultNo());
        childNo.setText(BRooms.getChildNo());
        checkIn.setText(BRooms.getCheckIn());
        childOut.setText(BRooms.getCheckOut());
     //   id.setText(BRooms.getBookingId());

        return listViewItem;
    }
}