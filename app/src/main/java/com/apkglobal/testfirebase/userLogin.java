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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class userLogin extends AppCompatActivity
{
    Button userLogin1;
    EditText userEmail1,userPassword1;
    ProgressDialog progress1 ;
    private FirebaseAuth mAuth1;
    public String email1,password1,ema,pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth1 = FirebaseAuth.getInstance();

        userEmail1 = (EditText) findViewById(R.id.userEmail1);
        userPassword1 = (EditText) findViewById(R.id.userPassword1);
        userLogin1 = (Button) findViewById(R.id.userLogin1);
        userLogin1.setEnabled(false);
        email1 = userEmail1.getText().toString().trim();
        password1 = userPassword1.getText().toString().trim();
        progress1 = new ProgressDialog(this);
        userEmail1.addTextChangedListener( new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String ema = userEmail1.getText().toString();
                String pas = userPassword1.getText().toString();
                if(ema.equals("")&&pas.equals("")){
                    userLogin1.setEnabled(false);
                }
                else if(!ema.equals("")&&pas.equals("")){
                    userLogin1.setEnabled(false);
                }
                else if(ema.equals("")&&!pas.equals("")){
                    userLogin1.setEnabled(false);
                }
                else
                    userLogin1.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        userPassword1.addTextChangedListener( new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                 ema = userEmail1.getText().toString();
                pas = userPassword1.getText().toString();
                if(ema.equals("")&&pas.equals("")){
                    userLogin1.setEnabled(false);
                }
                else if(!ema.equals("")&&pas.equals("")){
                    userLogin1.setEnabled(false);
                }
                else if(ema.equals("")&&!pas.equals("")){
                    userLogin1.setEnabled(false);
                }
                else
                    userLogin1.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        userLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(ema) || !TextUtils.isEmpty(pas)) {

                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (!ema.matches(emailPattern))
                    {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout=inflater.inflate(R.layout.toast, (ViewGroup)findViewById(R.id.toastDisplay));
                        Toast newToast = new Toast(getApplicationContext());
                        newToast.setDuration(Toast.LENGTH_LONG);
                        newToast.setView(layout);
                        newToast.show();
                    } else {
                        progress1.setMessage("Please Wait..,while checking your credentials");
                        progress1.show();

                        mAuth1.signInWithEmailAndPassword(ema, pas).addOnCompleteListener(userLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    progress1.dismiss();
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout=inflater.inflate(R.layout.toast3, (ViewGroup)findViewById(R.id.toastDisplay3));
                                    Toast newToast = new Toast(getApplicationContext());
                                    newToast.setDuration(Toast.LENGTH_LONG);
                                    newToast.setView(layout);
                                    newToast.show();
                                    Intent intent1 = new Intent(userLogin.this, ChooseActivity.class);
                                    startActivity(intent1);
                                    finish();
                                } else {
                                    Toast.makeText(userLogin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }



}
