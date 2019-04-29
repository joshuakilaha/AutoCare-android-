package com.example.myautocare.User;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myautocare.Activities.MainActivity;
import com.example.myautocare.Admin.AdminLogin;
import com.example.myautocare.MenuActivities.ContactUs;
import com.example.myautocare.R;
import com.google.android.gms.common.api.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    ///animation
    Animation frombotton,fromtop;

    EditText email, password,id,first_name,last_name;
    Button login;
    TextView signup, admin, forgot_password;
    CheckBox checkBox;

    String First_name;

    String User;

    JSONObject json_data;
    HttpURLConnection con;
    String query, results;
    ProgressDialog mProgressDialog;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        frombotton = AnimationUtils.loadAnimation(this,R.anim.frombutton);

        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        First_name = getIntent().getStringExtra("first_name");


        id = findViewById(R.id.user_id);
        email = findViewById(R.id.user_email);
        first_name = findViewById(R.id.user_first_name);
        last_name = findViewById(R.id.user_last_name);
        password = findViewById(R.id.user_password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        forgot_password = findViewById(R.id.forgot_password);
        admin = findViewById(R.id.admin);
        checkBox = findViewById(R.id.remember);




        login.startAnimation(frombotton);
        signup.startAnimation(frombotton);
        forgot_password.startAnimation(frombotton);
        admin.startAnimation(frombotton);
        checkBox.startAnimation(frombotton);
        email.startAnimation(fromtop);
        password.startAnimation(fromtop);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////move to main activity////


                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){
                    final AlertDialog.Builder alert = new
                            AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Failed");
                    alert.setMessage("Please fill in the details");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(@NonNull DialogInterface dialog, int whichButton)
                        {
                            dialog.cancel();
                        }
                    });
                    alert.show();

                } else {
                    new Login().execute();

                }
            }
        });

        /////checkbox/////


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent SignUp_user = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(SignUp_user);

            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////forgot password//////
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////to Admin///

               Intent admin_login = new Intent(LoginActivity.this, AdminLogin.class);
               startActivity(admin_login);



            }
        });


    }


    public  void Textcheck(){

        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){
            final AlertDialog.Builder alert = new
                    AlertDialog.Builder(LoginActivity.this);
            alert.setTitle("Failed");
            alert.setMessage("Please fill in the details");
            alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(@NonNull DialogInterface dialog, int whichButton)
                {
                    dialog.cancel();
                }
            });
            alert.show();

        }
    }



    final class Login extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Logging in, please wait...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", email.getText().toString().trim())
                        .appendQueryParameter("password", password.getText().toString().trim())
                        .appendQueryParameter("first_name", first_name.getText().toString().trim())
                        .appendQueryParameter("last_name", last_name.getText().toString().trim())
                        .appendQueryParameter("id_number", id.getText().toString().trim())
                        .appendQueryParameter("password",password.getText().toString().trim());


                query = builder.build().getEncodedQuery();
                String url = "https://beastly-defection.000webhostapp.com/AutoCare/profile.php";

                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0;Windows NT 5.1)");
                con.setRequestProperty("Accept-Language", "UTF-8");
                con.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new
                        OutputStreamWriter(con.getOutputStream());
                outputStreamWriter.write(query);
                outputStreamWriter.flush();
            } catch (Exception e) {

                android.util.Log.e("Fail 1", e.toString());
            }
            try {
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(con.getInputStream()));
                String line;
                StringBuffer sb = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    sb.append(line + "\n");
                }
                results = sb.toString();
            } catch (Exception e) {
                android.util.Log.e("Fail 2", e.toString());
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            try {
                json_data = new JSONObject(results);
                int code = (json_data.getInt("code"));
                if (code == 1) {

                    first_name.setText(json_data.getString("first_name"));
                    last_name.setText(json_data.getString("last_name"));
                    id.setText(json_data.getString("id_number"));

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    intent.putExtra("first_name",first_name.getText().toString());
                      intent.putExtra("last_name",last_name.getText().toString());
                             intent.putExtra("id_number",id.getText().toString());
                             intent.putExtra("email",email.getText().toString());
                             intent.putExtra("password",password.getText().toString());
                    startActivity(intent);


                } else {


                    final AlertDialog.Builder alert = new
                            AlertDialog.Builder(LoginActivity.this);
                    alert.setTitle("Failed");
                    alert.setMessage(" Failed to log in ");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(@NonNull DialogInterface dialog, int whichButton)
                        {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
            } catch (Exception e) {
                Log.e("Fail 3", e.toString());
            }
            mProgressDialog.dismiss();
        }
    }
}