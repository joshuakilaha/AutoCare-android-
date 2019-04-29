package com.example.myautocare.MenuActivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myautocare.R;
import com.example.myautocare.User.LoginActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Settings extends AppCompatActivity {

    EditText first,last,mail,idd,pass;
    Button update;

    String first_name, last_name, id, email, password;


    JSONObject json_data;
    HttpURLConnection con;
    String query, results;
    ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        first = findViewById(R.id.first);
        last = findViewById(R.id.last);
        idd = findViewById(R.id.id);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);


        update = findViewById(R.id.update);


        try {


            Intent intent = getIntent();

            first_name = intent.getStringExtra("first_name");
            last_name = intent.getStringExtra("last_name");
            id = intent.getStringExtra("id_number");
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");


            first.setText( first_name);
            last.setText( last_name);
            idd.setText(id);
            mail.setText(email);
            pass.setText(password);

        }
        catch (Exception e){

            Toast.makeText(this, "not working", Toast.LENGTH_SHORT).show();

        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new  Update().execute();
                Intent intent = new Intent(Settings.this, LoginActivity.class);

                startActivity(intent);
            }
        });



    }


    final class Update extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Settings.this);
            mProgressDialog.setMessage("Updating record please wait..");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
        @Override
        protected Void doInBackground(String... params) {
            try {
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("id_number", idd.getText().toString().trim())
                        .appendQueryParameter("first_name", first.getText().toString().trim())
                        .appendQueryParameter("email", mail.getText().toString().trim())
                        .appendQueryParameter("password", pass.getText().toString().trim())
                        .appendQueryParameter("last_name", last.getText().toString().trim());

                query = builder.build().getEncodedQuery();
                String url = "https://beastly-defection.000webhostapp.com/AutoCare/UpdateProfile.php";
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
                    final AlertDialog.Builder alert = new
                            AlertDialog.Builder(Settings.this);
                    alert.setTitle("Success");
                    alert.setMessage("Details have Updated");
                    alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(@NonNull DialogInterface dialog, int whichButton)
                        {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                } else {
                    final AlertDialog.Builder alert = new
                            AlertDialog.Builder(Settings.this);

                    alert.setTitle("Failed");
                    alert.setMessage("Updating Failed");
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
