package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailId,password;
    Button logIn,signUp;

    FirebaseAuth myFirebaseAuth;
    private FirebaseAuth.AuthStateListener myAuthStateListener;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myFirebaseAuth=FirebaseAuth.getInstance();

        emailId=findViewById(R.id.editText1);
        password=findViewById(R.id.editText2);
        logIn=findViewById(R.id.button1);
        signUp=findViewById(R.id.button2);

//        myAuthStateListener=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser myFirebaseUser =myFirebaseAuth.getCurrentUser();
//                if(myFirebaseUser!=null){
//                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
//                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
//                    startActivity(i);
//                }
//            }
//        };

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=emailId.getText().toString();
                final String pass=password.getText().toString();
                if(email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if(pass.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else {
                    myFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(email.equals("admin@gmail.com") && pass.equals("admin1")) {
                                    finish();
                                    Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                                    startActivity(i);
                                }
                                else {
                                    finish();
                                    Intent i = new Intent(LoginActivity.this,StudentActivity.class);
                                    startActivity(i);
                                }
                            }
                        }
                    });
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        if(myFirebaseAuth.getCurrentUser()!=null){
//            Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
//            finish();
//            Intent i=new Intent(LoginActivity.this,AdminActivity.class);
//            startActivity(i);
//        }
//    }
}
