package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
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
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText emailId,password;
    Button signUp,logIn;

    long studentid=0;
    int count=0;

    FirebaseAuth myFirebaseAuth;
    DatabaseReference databaseStudent;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseStudent= FirebaseDatabase.getInstance().getReference("students");
        databaseStudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    studentid=(dataSnapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myFirebaseAuth=FirebaseAuth.getInstance();

        emailId=findViewById(R.id.editText1);
        password=findViewById(R.id.editText2);
        signUp=findViewById(R.id.button1);
        logIn=findViewById(R.id.button2);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                if(email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if(pass.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if(pass.length()<6){
                    Toast.makeText(MainActivity.this,"Minimum length for password is 6 characters",Toast.LENGTH_SHORT).show();
                }
                else {
                    myFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if(user!=null){
                                    for(UserInfo profile: user.getProviderData()){
                                        String uid = profile.getUid();
                                        String name = profile.getDisplayName();
                                        String email = profile.getEmail();
                                        String id=user.getUid();
                                        Uri photoUrl = profile.getPhotoUrl();
                                        Student1 student=new Student1(studentid+1,id,uid,count);
                                        databaseStudent.child(String.valueOf(studentid+1)).setValue(student);
                                    }
                                }
                                finish();
                                Intent i=new Intent(MainActivity.this,StudentActivity.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Signup unsuccessful, Please try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}