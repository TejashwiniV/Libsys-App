package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RenewBook extends AppCompatActivity {

    EditText renewBookID,renewStdID;
    Button renewBook1;
    long issueid=0;
    FirebaseAuth myFirebaseAuth;
    long count=0;
    int rnCount=0;
    DatabaseReference databaseRenew;
    DatabaseReference renewCheck1;
    DatabaseReference renewCheck2;
    public static Date addDays(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,30);
        return cal.getTime();
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_book);

        databaseRenew= FirebaseDatabase.getInstance().getReference("issue");
        renewCheck1 = FirebaseDatabase.getInstance().getReference("books");
        renewCheck2 = FirebaseDatabase.getInstance().getReference("students");
        myFirebaseAuth=FirebaseAuth.getInstance();

        databaseRenew.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    issueid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        renewBookID=findViewById(R.id.issbkID);
        renewStdID=findViewById(R.id.issSTD);
        renewBook1=findViewById(R.id.renewBook);

        renewBook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(renewBookID.getText().toString()) && TextUtils.isEmpty(renewStdID.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Empty Fields !",Toast.LENGTH_SHORT).show();
                    renewBookID.setError("Enter Book ID !");
                    renewStdID.setError("Enter Student ID !");
                    return;
                }
                if(TextUtils.isEmpty(renewBookID.getText().toString())){
                    renewBookID.setError("Enter Book ID !");
                    return;
                }
                if(TextUtils.isEmpty(renewStdID.getText().toString())){
                    renewStdID.setError("Enter Student ID !");
                    return;
                }
                final int bookID=Integer.parseInt(renewBookID.getText().toString());
                final int stdID=Integer.parseInt(renewStdID.getText().toString());

                Query q = renewCheck1.child(String.valueOf(bookID)).orderByChild("bookId");
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            validate_std(bookID, stdID);
                            return;
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Invalid Book ID", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    private  void validate_std(final int bookId, final int stdID){
        Query q1 = renewCheck2.child(String.valueOf(stdID)).orderByChild("studentId");
        q1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                        renewBook(bookId, stdID);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Student ID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void renewBook(final int bookID,final int stdID){
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final Date date=new Date();
        final Date ret_date = addDays(date);

        databaseRenew.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count=0;
                for(DataSnapshot i:dataSnapshot.getChildren()) {
                    String N_Id = i.child("issueId").getValue().toString();
                    rnCount = Integer.parseInt(i.child("renewCount").getValue().toString());
                    //BK_ID[0] = i.child("bookID").getValue().toString();
                        if (i.child("bookID").getValue().toString().equals(String.valueOf(bookID)) && i.child("studentID").getValue().toString().equals(String.valueOf(stdID))) {
                            if (rnCount < 3) {
                            databaseRenew.child(N_Id).child("issueDate").setValue(sdf.format(date));
                            databaseRenew.child(N_Id).child("returnDate").setValue(sdf.format(ret_date));
                            databaseRenew.child(N_Id).child("renewCount").setValue(rnCount+1);
                            Toast.makeText(getApplicationContext(), "Book Renew Successful", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else if(rnCount>=3){
                            Toast.makeText(getApplicationContext(), "Maximum 3 Renewal Done", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    count++;
                }
                System.out.println("COUNT "+count);
                System.out.println("ISSUE_ID "+issueid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(count==issueid) {
            System.out.println("Book Not Issued");
            Toast.makeText(getApplicationContext(), "Book Not Issued", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(myFirebaseAuth.getCurrentUser()==null){
            finish();
            Intent i=new Intent(RenewBook.this,LoginActivity.class);
            startActivity(i);
        }
    }
}
