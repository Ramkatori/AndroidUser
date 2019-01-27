package com.apkglobal.testfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

        private DatabaseReference databaseReference;
         EditText editText;
        Button button ;
        EditText editText2;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText =(EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        button= (Button) findViewById(R.id.butt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("hello");
                HashMap<String ,String> hashmap = new HashMap<String, String>();
                hashmap.put("name",editText.getText().toString().trim());
                hashmap.put("email",editText2.getText().toString().trim());
                // addOnCompleteListener is added to  ensure that data is stored or not
                databaseReference.push().setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "task completed...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "not stored.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

}
