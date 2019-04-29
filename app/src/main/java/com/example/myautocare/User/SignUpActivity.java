package com.example.myautocare.User;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import com.example.myautocare.Activities.MainActivity;
import com.example.myautocare.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {
    Animation frombotton,fromtop;


    EditText first_name,last_name,email,id,password;
    Button signup;




    /////connecting to sql
    JSONObject json_data;
    HttpURLConnection con;
    String query, results;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        frombotton = AnimationUtils.loadAnimation(this,R.anim.frombutton);

        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtop);

        id = findViewById(R.id.user_id);
        first_name = findViewById(R.id.user_first_name);
        last_name = findViewById(R.id.user_last_name);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);


        signup = findViewById(R.id.signup_user);



        signup.startAnimation(frombotton);
        email.startAnimation(fromtop);
        password.startAnimation(fromtop);
        first_name.startAnimation(fromtop);
        last_name.startAnimation(fromtop);
        id.startAnimation(fromtop);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String First_name = first_name.getText().toString();
                String Last_name = last_name.getText().toString();
                String Id = id.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if(TextUtils.isEmpty(First_name) || TextUtils.isEmpty(Last_name) ||
                        TextUtils.isEmpty(Id)||TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){

                    final AlertDialog.Builder alert = new
                            AlertDialog.Builder(SignUpActivity.this);
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
                    new Signup().execute();

                }

                //////////////////////Signup/////////////////////////////

            }
        });


    }

    public void CheckText(){

        String First_name = first_name.getText().toString();
        String Last_name = last_name.getText().toString();
        String Id = id.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if(TextUtils.isEmpty(First_name) || TextUtils.isEmpty(Last_name) ||
                TextUtils.isEmpty(Id)||TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)){

            final AlertDialog.Builder alert = new
                    AlertDialog.Builder(SignUpActivity.this);
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

    final class Signup extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SignUpActivity.this);
            mProgressDialog.setMessage("Registering you're details, please wait..");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("id_number", id.getText().toString().trim())
                        .appendQueryParameter("first_name", first_name.getText().toString().trim())
                        .appendQueryParameter("last_name",last_name.getText().toString().trim())
                        .appendQueryParameter("email",email.getText().toString().trim())
                        .appendQueryParameter("password",password.getText().toString().trim());

                query = builder.build().getEncodedQuery();
               // String url = "https://beastly-defection.000webhostapp.com/AutoCare/signup.php";

                String url = "https://beastly-defection.000webhostapp.com/AutoCare/create.php";

                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
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
                    final AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);

                    Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                    intent.putExtra("first_name",first_name.getText());
                    startActivity(intent);


                    alert.setTitle("Success");
                    alert.setMessage("Registration Success");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(@NonNull DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                } else {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                    alert.setTitle("Failed");
                    alert.setMessage("Registering User Failed");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(@NonNull DialogInterface dialog, int whichButton) {
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
