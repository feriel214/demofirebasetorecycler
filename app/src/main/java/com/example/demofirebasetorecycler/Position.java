package com.example.demofirebasetorecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Position extends AppCompatActivity {
    TextView text1,text2,text3,text4,text5;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button btLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        btLocation=findViewById(R.id.bt_location);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        text3=findViewById(R.id.text3);
        text4=findViewById(R.id.text4);
        text5=findViewById(R.id.text5);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission
                if(ActivityCompat.checkSelfPermission(Position.this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    //When permession granted
                    getLocation();
                }else{
                    //When permession is denied
                    ActivityCompat.requestPermissions(Position.this,new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },44 );
                }
            }
        });

    }
    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize Location
                Location location=task.getResult();
                if(location!=null){
                    try {
                        //Initialize Geocoder
                        Geocoder geocoder=new Geocoder(Position.this, Locale.getDefault());
                        //Initialize adress list
                        List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        //Set latitude on TextView
                        text1.setText(Html.fromHtml("<b>Latitude: </b><br>"+addresses.get(0).getLatitude()));
                        //Set latitude on TextView
                        text2.setText(Html.fromHtml("<b>Longitude: </b><br>"+addresses.get(0).getLongitude()));
                        //Set latitude on TextView
                        text3.setText(Html.fromHtml("<b>Ville: </b><br>"+addresses.get(0).getCountryName()));
                        //Set latitude on TextView
                        text1.setText(Html.fromHtml("<b>Location: </b><br>"+addresses.get(0).getLocality()));
                        //Set latitude on TextView
                        text1.setText(Html.fromHtml("<b>Adresse: </b><br>"+addresses.get(0).getAddressLine(0)));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}