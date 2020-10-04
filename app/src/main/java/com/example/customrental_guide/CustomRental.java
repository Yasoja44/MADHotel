package com.example.customrental_guide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomRental extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_rental);


    }


    public void Fragment(View view) {
        Fragment fragment;

        if (view == findViewById(R.id.btnBike)) {
            fragment = new BikeFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragView, fragment);
            ft.commit();
        } else if (view == findViewById(R.id.btnCar)) {
            fragment = new CarFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragView, fragment);
            ft.commit();
        } else if (view == findViewById(R.id.btnBicycle)) {
            fragment = new BicycleFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragView, fragment);
            ft.commit();
        } else if (view == findViewById(R.id.btnVan)) {
            fragment = new VanFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragView, fragment);
            ft.commit();
        }
    }
}