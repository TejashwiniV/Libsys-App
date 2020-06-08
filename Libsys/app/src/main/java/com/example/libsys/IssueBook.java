package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import java.sql.Date;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IssueBook extends AppCompatActivity {
    Button issbookSub1;
    EditText issbkID1,issSTD1;
    long issueid;
    long count=0;
    int bookID;
    int stdID;
    String BKD="";
    DatabaseReference databaseIssue;
    DatabaseReference issueCheck1;
    DatabaseReference issueCheck2;
    public static Date addDays(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,15);
        return cal.getTime();
    }
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        databaseIssue= FirebaseDatabase.getInstance().getReference("issue");
        issueCheck1 = FirebaseDatabase.getInstance().getReference("books");
        issueCheck2 = FirebaseDatabase.getInstance().getReference("students");
        databaseIssue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    issueid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        issbookSub1 = findViewById(R.id.issbookSub);
        issbkID1 = findViewById(R.id.issbkID);
        issSTD1 = findViewById(R.id.issSTD);


        issbookSub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(issbkID1.getText().toString()) && TextUtils.isEmpty(issSTD1.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Empty Fields!",Toast.LENGTH_SHORT).show();
                    issbkID1.setError("Enter Book ID !");
                    issSTD1.setError("Enter Student ID !");
                    return;
                }
                if(TextUtils.isEmpty(issbkID1.getText().toString())){
                    issbkID1.setError("Enter Book ID !");
                    return;
                }
                if(TextUtils.isEmpty(issSTD1.getText().toString())){
                    issSTD1.setError("Enter Student ID !");
                    return;
                }
                bookID=Integer.parseInt(issbkID1.getText().toString());
                stdID=Integer.parseInt(issSTD1.getText().toString());
                Query q = issueCheck1.child(String.valueOf(bookID)).orderByChild("bookId");
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            int a = Integer.parseInt(dataSnapshot.child("remBookCount").getValue().toString());
                            if(a>=1) {
                                System.out.println("remBook "+a);
                                validate_std(a, bookID, stdID);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "All Books Issued", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Invalid Book ID", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }


    private  void validate_std(final int t,final int bookId, final int stdID){
        Query q1 = issueCheck2.child(String.valueOf(stdID)).orderByChild("studentId");
        q1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    int a1 = Integer.parseInt(dataSnapshot.child("borrowedBooks").getValue().toString());
                    System.out.println("Borrowed "+a1);
                    if(a1<3) {
                        issueBook(t, a1, bookId, stdID);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Max three Books Issued", Toast.LENGTH_SHORT).show();
                    }
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

    private void issueBook(final int t,final int a2,final int bookID,final int stdID){
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        final Date date=new Date();
        final Date ret_date = addDays(date);
        databaseIssue.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count=0;
                for(DataSnapshot i:dataSnapshot.getChildren()) {
                    //String N_Id = i.child("issueId").getValue().toString();
                    System.out.println("Inside FOR Update");
                    BKD = i.child("bookID").getValue().toString();
                    System.out.println("BK_ID "+BKD);
                    if (i.child("bookID").getValue().toString().equals(String.valueOf(bookID)) && i.child("studentID").getValue().toString().equals(String.valueOf(stdID))) {
                        System.out.println("Inside Update");
                        Toast.makeText(getApplicationContext(), "Book Already Issued", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    count++;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        //System.out.println("COUNT 1 "+ count[0]);
        //System.out.println("ISSUE_ID 1 "+issueid);
        if(count==issueid) {
            int b = t-1;
            int c = a2+1;
            System.out.println("remaining "+b);
            issueCheck1.child(String.valueOf(bookID)).child("remBookCount").setValue(b);
            issueCheck2.child(String.valueOf(stdID)).child("borrowedBooks").setValue(c);
            IssueBook1 book = new IssueBook1(issueid + 1, bookID, stdID, sdf.format(date), sdf.format(ret_date),0);
            databaseIssue.child(String.valueOf(issueid + 1)).setValue(book);
            Toast.makeText(getApplicationContext(), "Book Issued Successful", Toast.LENGTH_SHORT).show();
        }
    }
}


