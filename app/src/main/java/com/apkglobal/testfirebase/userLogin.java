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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth1 = FirebaseAuth.getInstance();

        userEmail1 = (EditText) findViewById(R.id.userEmail1);
        userPassword1 = (EditText) findViewById(R.id.userPassword1);
        userLogin1 = (Button) findViewById(R.id.userLogin1);
        progress1 = new ProgressDialog(this);

        userLogin1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String email1 = userEmail1.getText().toString().trim();
                final String password1 = userPassword1.getText().toString().trim();

                if ( !TextUtils.isEmpty(email1) || !TextUtils.isEmpty(password1))
                {
                    progress1.setMessage("Please Wait..,while checking your credentials");
                    progress1.show();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (!email1.matches(emailPattern))
                    {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {


                   mAuth1.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(userLogin.this, new OnCompleteListener<AuthResult>()
                   {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task)
                       {

                           if (task.isSuccessful())
                           {
                               progress1.dismiss();
                               Toast.makeText(userLogin.this, "Login successfully", Toast.LENGTH_SHORT).show();
                               Intent intent1 = new Intent(userLogin.this, userWants.class);
                               startActivity(intent1);
                               finish();
                           }
                           else
                           {
                               Toast.makeText(userLogin.this, "task.getException().getMessage()", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                }}
            }
       });
    }

}
