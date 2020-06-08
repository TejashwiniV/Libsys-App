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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReturnBook extends AppCompatActivity {
    long issueid=0;
    long count=0;
    String BKD;
    Button ret;
    EditText retbkID1,retSTD1;
    DatabaseReference databaseReturn;
    DatabaseReference retBook;
    DatabaseReference retStd;
    FirebaseAuth myFirebaseAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);

        myFirebaseAuth=FirebaseAuth.getInstance();

        ret = findViewById(R.id.retbookSub);
        retbkID1 = findViewById(R.id.retbkID);
        retSTD1 = findViewById(R.id.retSTD);

        databaseReturn= FirebaseDatabase.getInstance().getReference("issue");
        retBook = FirebaseDatabase.getInstance().getReference("books");
        retStd = FirebaseDatabase.getInstance().getReference("students");

        databaseReturn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    issueid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(retbkID1.getText().toString().isEmpty() && retSTD1.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty Fields!",Toast.LENGTH_SHORT).show();
                    retbkID1.setError("Enter Book ID !");
                    retSTD1.setError("Enter Student ID !");
                    return;
                }
                if(retbkID1.getText().toString().isEmpty()){
                    retbkID1.setError("Enter Book ID !");
                    return;
                }
                if(retSTD1.getText().toString().isEmpty()){
                    retSTD1.setError("Enter Student ID !");
                    return;
                }
                final int bookID=Integer.parseInt(retbkID1.getText().toString());
                final int stdID=Integer.parseInt(retSTD1.getText().toString());

                Query q = retBook.child(String.valueOf(bookID)).orderByChild("bookId");
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            int remBook = Integer.parseInt(dataSnapshot.child("remBookCount").getValue().toString());
                            return_book(bookID,stdID,remBook+1);
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

    private void return_book(final int bookID, final int stdID, final int remBook){
        Query q1 = retStd.child(String.valueOf(stdID)).orderByChild("studentId");
        q1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    int borrowedBook = Integer.parseInt(dataSnapshot.child("borrowedBooks").getValue().toString());
                    returnBook(bookID,stdID,remBook,borrowedBook-1);
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

    private void returnBook(final int bookID, final int stdID, final int remBook, final int borrowedBook){
        databaseReturn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count=0;
                for(DataSnapshot i:dataSnapshot.getChildren()) {
                    String N_Id = i.child("issueId").getValue().toString();
                    System.out.println("Inside FOR Update");
                    BKD = i.child("bookID").getValue().toString();
                    System.out.println("BK_ID "+BKD);
                    if (i.child("bookID").getValue().toString().equals(String.valueOf(bookID)) && i.child("studentID").getValue().toString().equals(String.valueOf(stdID))) {
                        System.out.println("Inside Update");
                        databaseReturn.child(N_Id).child("bookID").setValue(0);
                        databaseReturn.child(N_Id).child("issueDate").setValue("");
                        databaseReturn.child(N_Id).child("issueId").setValue(0);
                        databaseReturn.child(N_Id).child("returnDate").setValue("");
                        databaseReturn.child(N_Id).child("studentID").setValue(0);
                        databaseReturn.child(N_Id).child("renewCount").setValue(0   );
                        retBook.child(String.valueOf(bookID)).child("remBookCount").setValue(remBook);
                        retStd.child(String.valueOf(stdID)).child("borrowedBooks").setValue(borrowedBook);
                        Toast.makeText(getApplicationContext(), "Book Returned", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    count++;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(count==issueid) {
            Toast.makeText(getApplicationContext(), "Book was'nt Issued", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(myFirebaseAuth.getCurrentUser()==null){
            finish();
            Intent i=new Intent(ReturnBook.this,LoginActivity.class);
            startActivity(i);
        }
    }
}
