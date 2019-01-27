package com.apkglobal.testfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user extends AppCompatActivity {
   Button userRegister;

   EditText userEmail,userMobileNo,userName,userPassword;
   TextView loginLink;
   ProgressDialog progress ;
   private FirebaseAuth mAuth;
   private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
              FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
              if(user!=null)
              {
                  Intent intent2=new Intent(user.this,userWants.class);
                  startActivity(intent2);
                  finish();
              }
            }
        };


        userRegister = (Button) findViewById(R.id.userRegister);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userMobileNo = (EditText) findViewById(R.id.userMobileNo);
        userName = (EditText) findViewById(R.id.userName);
        userPassword = (EditText) findViewById(R.id.userPassword);
        progress = new ProgressDialog(this);
       loginLink = (TextView)findViewById(R.id.loginLink);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(user.this,userLogin.class);
                startActivity(intent2);
                finish();
            }
        });

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = userName.getText().toString().trim();
                final String email = userEmail.getText().toString().trim();
                final String password = userPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password))
                {
                    progress.setMessage("Registering,Please Wait..");
                    progress.show();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (!email.matches(emailPattern))
                    {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    } else {



                    final String mobile = userMobileNo.getText().toString().trim();
                    if(mobile.length()<10)
                    {
                        Toast.makeText(user.this, "please enter valid mobile no...!", Toast.LENGTH_SHORT).show();
                    }
                    else {


                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(user.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progress.dismiss();
                                FirebaseUser userId = task.getResult().getUser();
                                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("choice").child("user").child(userId.getUid());
                                currentUser.setValue("true");

                                currentUser = FirebaseDatabase.getInstance().getReference().child("choice").child("User").child("MobileNo");
                                currentUser.setValue(mobile);
                                currentUser = FirebaseDatabase.getInstance().getReference().child("choice").child("User").child("Name");
                                currentUser.setValue(name);

                                Toast.makeText(user.this, "sign up ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(user.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }}}
            }
        });
    }
    @Override
    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener( authStateListener);

    }
    @Override
    public void onStop()
    {
      super.onStop();
      mAuth.addAuthStateListener(authStateListener);
    }


}
