package com.example.myautocare.Admin;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myautocare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminSignup extends AppCompatActivity {

    private static final String TAG = "AdminSignup";

    EditText Admin_email,Admin_password;
    private Button signup_admin;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);


        Admin_email = findViewById(R.id.admin_email);
        Admin_password= findViewById(R.id.admin_password);
        signup_admin = findViewById(R.id.signup_admin);

        mAuth = FirebaseAuth.getInstance();



        signup_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////signup////////
                adminSignUp();


            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUserAdmin = mAuth.getCurrentUser();
        //updateUI(currentUserAdmin);
    }


//****************************************************************Admin Sign up***********************************************************
    private void adminSignUp(){
        final String email = Admin_email.getText().toString();
        final String password = Admin_password.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(AdminSignup.this,"Input details on all fields",Toast.LENGTH_LONG).show();
        }else {

        }

        final ProgressDialog progressDialog = ProgressDialog.show(AdminSignup.this, "Please wait...","Processing...",true);
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //onSuccess
                                Log.d(TAG, "AdminDetails:sucess");
                                FirebaseUser Admin = mAuth.getCurrentUser();
                                progressDialog.dismiss();
                                Toast.makeText(AdminSignup.this, "Signup Success", Toast.LENGTH_SHORT).show();


                                //  updateUI(Admin);
                            } else {
                                //onFailed
                                Log.w(TAG, "AdminDetails:failure", task.getException());
                                Toast.makeText(AdminSignup.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                Toast.makeText(AdminSignup.this, "Failed to Signup Admin", Toast.LENGTH_SHORT).show();
                                //  updateUI(null);
                                progressDialog.dismiss();

                            }

                        }
                    });
        }

    }



}
