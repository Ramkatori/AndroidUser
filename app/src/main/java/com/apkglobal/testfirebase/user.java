package com.apkglobal.testfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
    String name,email,password,mobile;
   EditText userEmail,userMobileNo,userName,userPassword;
   CheckBox checkBox;
   TextView userLink;
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
                  Intent intent2=new Intent(user.this,ChooseActivity.class);
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
       userLink = (TextView)findViewById(R.id.userLink);
       checkBox = (CheckBox)findViewById(R.id.checkBox);


        userLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(user.this,userLogin.class);
                startActivity(intent2);
                finish();
            }
        });



        userName.addTextChangedListener(loginText);
        userEmail.addTextChangedListener(loginText);
        userPassword.addTextChangedListener(loginText);
        userMobileNo.addTextChangedListener(loginText);
        checkBox.addTextChangedListener(loginText);


        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password))
                {

                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (!email.matches(emailPattern))
                    {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout=inflater.inflate(R.layout.toast, (ViewGroup)findViewById(R.id.toastDisplay));
                        Toast newToast = new Toast(getApplicationContext());
                        newToast.setDuration(Toast.LENGTH_LONG);
                        newToast.setView(layout);
                        newToast.show();
                    }
                    else
                    {

                    if(mobile.length()<10)
                    {

                        LayoutInflater inflater = getLayoutInflater();
                        View layout=inflater.inflate(R.layout.toast1, (ViewGroup)findViewById(R.id.toastDisplay1));
                        Toast newToast = new Toast(getApplicationContext());
                        newToast.setDuration(Toast.LENGTH_LONG);
                        newToast.setView(layout);
                        newToast.show();
                    }
                    else
                        {
                            progress.setMessage("Registering,Please Wait..");
                            progress.show();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(user.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progress.dismiss();
                                FirebaseUser userId = task.getResult().getUser();
                                DatabaseReference currentUser = FirebaseDatabase.getInstance().getReference().child("choice").child("User").child(userId.getUid());
                                currentUser.setValue("true");


                                currentUser = FirebaseDatabase.getInstance().getReference().child("choice").child("User").child(userId.getUid()).child("Name");
                                currentUser.setValue(name);
                                currentUser = FirebaseDatabase.getInstance().getReference().child("choice").child("User").child(userId.getUid()).child("EmailId");
                                currentUser.setValue(email);
                                currentUser = FirebaseDatabase.getInstance().getReference().child("choice").child("User").child(userId.getUid()).child("Password");
                                currentUser.setValue(password);
                                currentUser = FirebaseDatabase.getInstance().getReference().child("choice").child("User").child(userId.getUid()).child("MobileNo");
                                currentUser.setValue(mobile);

                                LayoutInflater inflater = getLayoutInflater();
                                View layout=inflater.inflate(R.layout.toast2, (ViewGroup)findViewById(R.id.toastDisplay2));
                                Toast newToast = new Toast(getApplicationContext());
                                newToast.setDuration(Toast.LENGTH_LONG);
                                newToast.setView(layout);
                                newToast.show();
                            } else {
                                Toast.makeText(user.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }}}
            }
        });
    }
    private TextWatcher loginText = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            name = userName.getText().toString().trim();
            email = userEmail.getText().toString().trim();
            password = userPassword.getText().toString().trim();
            mobile = userMobileNo.getText().toString().trim();



            if(!email.isEmpty() && !password.isEmpty() && !mobile.isEmpty() && !name.isEmpty() )
            {
                userRegister.setEnabled(true);
        }
        else
            {
                userRegister.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
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
