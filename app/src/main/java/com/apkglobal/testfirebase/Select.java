package com.apkglobal.testfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Select extends AppCompatActivity {
   Button button1,button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
    button1.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
           Intent intent1 = new Intent(Select.this,user.class);
           startActivity(intent1);
           finish();
        }
    });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Select.this,serviceProvider.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}