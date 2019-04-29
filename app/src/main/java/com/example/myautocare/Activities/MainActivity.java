package com.example.myautocare.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myautocare.MenuActivities.AboutUs;
import com.example.myautocare.MenuActivities.Brands;
import com.example.myautocare.MenuActivities.ContactUs;
import com.example.myautocare.MenuActivities.MyParts;
import com.example.myautocare.MenuActivities.Mylocation;
import com.example.myautocare.MenuActivities.Profile;
import com.example.myautocare.MenuActivities.Settings;
import com.example.myautocare.R;
import com.example.myautocare.User.LoginActivity;
import com.example.myautocare.User.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {




    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView mNavigationView;

    String first_name,email,id,last_name,password;

    Button Read;
    ImageView Cover;

    /////webview////
    WebView webView;
    ProgressBar progressBar;


    TextView nametextview,mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Read = findViewById(R.id.readmore);
        Cover = findViewById(R.id.coverphoto);

        Cover.animate().scaleX(-2).scaleY(2).setDuration(5000).start();



        Read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Webview.class);
                startActivity(intent);
            }
        });



        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = findViewById(R.id.design_navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);


    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){


            try {
                nametextview = findViewById(R.id.user_first_name1);
                mail = findViewById(R.id.useremail);

                Intent intent = getIntent();
                first_name = intent.getStringExtra("first_name");
                last_name = intent.getStringExtra("last_name");
                id = intent.getStringExtra("id_number");
                email = intent.getStringExtra("email");
                password = intent.getStringExtra("password");

                nametextview.setText(first_name);
                mail.setText(email);

                Toast.makeText(this, " Welcome " + first_name, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {


            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.profile:
                Intent profile = new Intent(MainActivity.this, Profile.class);

                profile.putExtra("first_name", first_name);
                profile.putExtra("last_name",last_name);
                profile.putExtra("id_number",id);
                profile.putExtra("email",email);
                profile.putExtra("password",password);
                startActivity(profile);

                Toast.makeText(MainActivity.this, "Profile opened", Toast.LENGTH_SHORT).show();
                break;

            case R.id.brands:
                Intent brands = new Intent(MainActivity.this, Brands.class);
                startActivity(brands);
                Toast.makeText(MainActivity.this, "Brands opened", Toast.LENGTH_SHORT).show();
                break;

            case R.id.myparts:
                Intent myparts = new Intent(MainActivity.this, MyParts.class);
                startActivity(myparts);
                Toast.makeText(MainActivity.this, "My Parts opened..", Toast.LENGTH_SHORT).show();
                break;


            case R.id.settings:
                Intent settings = new Intent(MainActivity.this, Settings.class);

                settings.putExtra("first_name", first_name);
                settings.putExtra("last_name",last_name);
                settings.putExtra("id_number",id);
                settings.putExtra("email",email);
                settings.putExtra("password",password);
                startActivity(settings);

                Toast.makeText(MainActivity.this, "settings opened...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.contactus:
                Intent contactus = new Intent(MainActivity.this, ContactUs.class);
                startActivity(contactus);
                Toast.makeText(MainActivity.this, "Contact Us opened...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:



                final AlertDialog logout = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Logout?")

                        //.setIcon(R.drawable.ic_done_black_48dp) //will replace icon with name of existing icon from project
                        .setCancelable(false)

                        //set three option buttons
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                //Log out
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();


                            }
                        })//setPositiveButton

                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Do nothing
                            }
                        })

                        .create();
                logout.show();


                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }




    ////////////////////////////map//////////////////////





}


