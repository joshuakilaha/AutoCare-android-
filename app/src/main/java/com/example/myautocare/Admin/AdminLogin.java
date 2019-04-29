package com.example.myautocare.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myautocare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {

    private static final String TAG = "AdminLogin";

    EditText Admin_email,Admin_password;
    private Button login_admin;
    TextView signup;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        Admin_email = findViewById(R.id.admin_email);
        Admin_password= findViewById(R.id.admin_password);
        login_admin = findViewById(R.id.login_admin);
        signup = findViewById(R.id.signup_admin);


        mAuth =FirebaseAuth.getInstance();


        login_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////login admin////////
                SigninAdmin();


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signup_admin = new Intent(AdminLogin.this,AdminSignup.class);
                startActivity(signup_admin);

            }
        });
    }


    private void SigninAdmin(){

        final String email = Admin_email.getText().toString();
        final String password = Admin_password.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(AdminLogin.this,"Input details on all fields",Toast.LENGTH_LONG).show();
        }else {

        }

        final ProgressDialog progressDialog = ProgressDialog.show(AdminLogin.this, "Please wait...","Processing...",true);
        {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "SignInWithEmail:success");
                                FirebaseUser Admin = mAuth.getCurrentUser();
                                progressDialog.dismiss();
                                Toast.makeText(AdminLogin.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent toAdminHome = new Intent(AdminLogin.this,AdminHome.class);
                                startActivity(toAdminHome);
                            }
                            else {
                                Log.w(TAG,"SignInwithEmail:failure", task.getException());
                                Toast.makeText(AdminLogin.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });
        }

    }



}
