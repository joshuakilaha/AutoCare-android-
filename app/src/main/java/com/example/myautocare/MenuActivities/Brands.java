package com.example.myautocare.MenuActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.myautocare.Activities.MainActivity;
import com.example.myautocare.Admin.AdminViews.BMW.ImagesActivity;
import com.example.myautocare.Admin.AdminViews.Mercedes.ImagesMercActivity;
import com.example.myautocare.Admin.AdminViews.RangeRover.ImagesRangeActivity;
import com.example.myautocare.Admin.AdminViews.Toyota.ImagesToyotaActivity;
import com.example.myautocare.MenuActivities.BrandActivities.BrandUserViews.BmwUserView;
import com.example.myautocare.MenuActivities.BrandActivities.BrandUserViews.MercedesUserView;
import com.example.myautocare.MenuActivities.BrandActivities.BrandUserViews.RangeUserView;
import com.example.myautocare.MenuActivities.BrandActivities.BrandUserViews.ToyotaUserView;
import com.example.myautocare.MenuActivities.BrandActivities.Mercedes;
import com.example.myautocare.MenuActivities.BrandActivities.Toyota;
import com.example.myautocare.R;

import java.io.InputStream;

public class Brands extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ViewFlipper brandflip,brandflip2,brandflip3,brandflip4;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        brandflip = findViewById(R.id.brandflip);
        brandflip2 = findViewById(R.id.brandflip2);
        brandflip3 = findViewById(R.id.brandflip3);
        brandflip4 = findViewById(R.id.brandflip4);


        ///////////////////////////////Navigation///////////////////////////////////////////////

        mDrawerLayout = findViewById(R.id.drawer_layout);



            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
            mDrawerLayout.addDrawerListener(mToggle);
            mToggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mNavigationView = findViewById(R.id.design_navigation_view);
            mNavigationView.setNavigationItemSelectedListener(this);
        ////////////////////////////////////////////////////////////////////////////////////

        BMW();
        mercedes();
        range();
        toyota();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.profile:
                Intent profile = new Intent(Brands.this, Profile.class);
                startActivity(profile);

                Toast.makeText(Brands.this, "Profile opened", Toast.LENGTH_SHORT).show();
                break;

            case R.id.brands:
                Intent brands = new Intent(Brands.this, Brands.class);
                startActivity(brands);
                Toast.makeText(Brands.this, "Brands opened", Toast.LENGTH_SHORT).show();
                break;

            case R.id.myparts:
                Intent myparts = new Intent(Brands.this, MyParts.class);
                startActivity(myparts);
                Toast.makeText(Brands.this, "My Parts opened..", Toast.LENGTH_SHORT).show();
                break;


            case R.id.settings:
                Intent settings = new Intent(Brands.this, Settings.class);
                startActivity(settings);
                Toast.makeText(Brands.this, "settings opened...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.contactus:
                Intent contactus = new Intent(Brands.this, ContactUs.class);
                startActivity(contactus);
                Toast.makeText(Brands.this, "Contact Us opened...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:

                Toast.makeText(Brands.this, "Logging out...", Toast.LENGTH_SHORT).show();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }


    //////////////////////////////////////////////////Flipper/////////////////////////////////////////////////////////////////////


///////////////////////////////////////////BMW/////////////////////////////

    public void BMWflip(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        brandflip3.addView(imageView);
        brandflip3.setFlipInterval(5000);
        brandflip3.setAutoStart(true);


        brandflip3.setInAnimation(this,android.R.anim.slide_in_left);
        brandflip3.setInAnimation(this,android.R.anim.slide_in_left);



    }



    public void BMW(){

       int images [] = {R.drawable.bmwlogo, R.drawable.b1, R.drawable.b2, R.drawable.b3};

        for (int image: images){
            BMWflip(image);
        }

        brandflip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bmw = new Intent(Brands.this, BmwUserView.class);
                startActivity(bmw);
                Toast.makeText(Brands.this, "Parts BMW", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /////////////////////////////////////////////////BENZ//////////////////////////////////////////////

    public void mercedesflip(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        brandflip4.addView(imageView);
        brandflip4.setFlipInterval(4000);
        brandflip4.setAutoStart(true);


        brandflip4.setInAnimation(this, android.R.anim.slide_in_left);
        brandflip4.setInAnimation(this, android.R.anim.slide_in_left);
    }


    public void mercedes(){

        int images [] = {R.drawable.benzlogo, R.drawable.m1,R.drawable.m2,R.drawable.m3};
        for (int image: images){
            mercedesflip(image);
        }

        brandflip4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent merc = new Intent(Brands.this, MercedesUserView.class);
                startActivity(merc);

                Toast.makeText(Brands.this, "Parts Mercedes", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //////////////////////////////////////////////////////////Range////////////////////////////////////////////////////////

    public void rangeflip(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        brandflip.addView(imageView);
        brandflip.setFlipInterval(5000);
        brandflip.setAutoStart(true);

        brandflip.setInAnimation(this,android.R.anim.slide_in_left);
        brandflip.setInAnimation(this,android.R.anim.slide_in_left);



    }

    public void range(){

        int images [] = {R.drawable.rlogo, R.drawable.r4,R.drawable.r5,R.drawable.r3};
        for (int image: images){
            rangeflip(image);
        }

        brandflip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rang = new Intent(Brands.this, RangeUserView.class);
                startActivity(rang);
                Toast.makeText(Brands.this, "Parts Rangerover", Toast.LENGTH_SHORT).show();
            }
        });

    }



///////////////////////////////////////////////////Toyota//////////////////////////////////////////////////////////

    public void toyotaflip(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        brandflip2.addView(imageView);
        brandflip2.setFlipInterval(4000);
        brandflip2.setAutoStart(true);




        brandflip2.setInAnimation(this,android.R.anim.slide_in_left);
        brandflip2.setInAnimation(this,android.R.anim.slide_in_left);



    }

    public void toyota(){

        int images [] = {R.drawable.toyotalogo, R.drawable.t1,R.drawable.t2,R.drawable.t3};
        for (int image: images){
            toyotaflip(image);
        }

        brandflip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toyota = new Intent(Brands.this, ToyotaUserView.class);
                startActivity(toyota);
                Toast.makeText(Brands.this, "Parts Toyota", Toast.LENGTH_SHORT).show();
            }
        });

    }


////////////////////////////////////////url image//////////////////////////////////////////////////////////




}
