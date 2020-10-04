package com.example.madapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity nContext;
    List<BookRoom> BookingDetailList;


    public ListAdapter(Activity nContext, List<BookRoom> bookingDetailList) {
        super(nContext,R.layout.bookig_details_list,bookingDetailList);
        this.nContext=nContext;
        this.BookingDetailList=bookingDetailList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = nContext.getLayoutInflater();
        if(convertView==null) {

            convertView = inflater.inflate(R.layout.bookig_details_list, parent, false);

            TextView roomNo = convertView.findViewById(R.id.lb_roomNo4);
            TextView adultNo = convertView.findViewById(R.id.lb_adultNo1);
            TextView childNo = convertView.findViewById(R.id.lb_childNo1);
            TextView checkIn = convertView.findViewById(R.id.lb_checkIn1);
            TextView childOut = convertView.findViewById(R.id.lb_checkOut1);

            BookRoom br = BookingDetailList.get(position);

            roomNo.setText(br.getRoomNo());
            adultNo.setText(br.getAdultNo());
            childNo.setText(br.getChildNo());
            checkIn.setText(br.getCheckIn());
            childOut.setText(br.getCheckOut());

            convertView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    removeItem(position);

                }

            });
        }
            return convertView;


    }

    private void  removeItem(final int position){

       // AlertDialog.Builder builder = new AlertDialog.Builder()
        BookingDetailList.remove(position);
    };

}
