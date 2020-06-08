package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddBook extends AppCompatActivity {

    Button bksubmit;
    EditText bkna,bkau,bkcn;

    long bookid=0;
    String res="";
    int count=0;

    DatabaseReference databaseBook;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        databaseBook= FirebaseDatabase.getInstance().getReference("books");
        databaseBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    bookid=(dataSnapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bksubmit = findViewById(R.id.bookSub);
        bkna = findViewById(R.id.bkname);
        bkau = findViewById(R.id.bkauth);
        bkcn = findViewById(R.id.bkcnt);

        bksubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(bkna.getText().toString().isEmpty() && bkau.getText().toString().isEmpty() && bkcn.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty Fields",Toast.LENGTH_SHORT).show();
                    bkna.setError("Enter Book Name");
                    bkau.setError("Enter Author Name");
                    bkcn.setError("Enter Number of Books");
                    return;
                }
                if(bkna.getText().toString().isEmpty()){
                    bkna.setError("Enter Book Name");
                    return;
                }
                if(bkau.getText().toString().isEmpty()){
                    bkau.setError("Enter Author Name");
                    return;
                }
                if(bkcn.getText().toString().isEmpty()){
                    bkcn.setError("Enter Number of Books");
                    return;
                }
                addBook();
            }
        });
    }

    private void addBook() {
        final String book_name = bkna.getText().toString();
        final String book_author = bkau.getText().toString();
        final int book_count=Integer.parseInt(bkcn.getText().toString());


        databaseBook.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count=0;
                for(DataSnapshot i:dataSnapshot.getChildren()) {
                    String N_Id = i.child("bookId").getValue().toString();
                    System.out.println("BOOKID"+N_Id);
                    int new_cnt = Integer.parseInt(i.child("bookCount").getValue().toString()) + book_count;
                    int new_rem_cnt =Integer.parseInt(i.child("remBookCount").getValue().toString()) + book_count;
                    if (i.child("bookName").getValue().toString().equals(book_name) && i.child("bookAuthor").getValue().toString().equals(book_author)) {
                        AddBook1 book = new AddBook1(Integer.parseInt(N_Id),book_name,book_author,new_cnt,new_rem_cnt);
                        databaseBook.child(N_Id).setValue(book);
                        Toast.makeText(getApplicationContext(), "Book Updated Successful", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    count++;
                }
                System.out.println("COUNT "+count);
                System.out.println("BOOK_ID "+bookid);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(count==bookid){
            AddBook1 book = new AddBook1(bookid+1, book_name,book_author,book_count,book_count);
            databaseBook.child(String.valueOf(bookid+1)).setValue(book);
            Toast.makeText(getApplicationContext(), "Book Added Successful", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
