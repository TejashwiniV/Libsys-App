package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewBorrowed extends AppCompatActivity {
    FirebaseAuth myFirebaseAuth;
    DatabaseReference borrowed;
    DatabaseReference issued;
    DatabaseReference bkissued;
    String uid;
    ArrayList<String> issueDate = new ArrayList<>();
    ArrayList<String> returnDate =new ArrayList<>();
    ArrayList<Integer> bookID =new ArrayList<>();
    ArrayList<Integer> renewCnt = new ArrayList<>();
    ArrayList<String> bkname=new ArrayList<>();
    ArrayList<String> authname=new ArrayList<>();
    TextView t ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_borrowed);
        myFirebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user = myFirebaseAuth.getCurrentUser();
        if(user!=null){
            for(UserInfo profile: user.getProviderData()){
                uid = profile.getUid();
            }
        }
        t=findViewById(R.id.borrow);


        borrowed= FirebaseDatabase.getInstance().getReference("students");
        issued= FirebaseDatabase.getInstance().getReference("issue");
        bkissued=FirebaseDatabase.getInstance().getReference("books");
        borrowed.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot i:dataSnapshot.getChildren()){
                        if(i.child("userEmail").getValue().toString().equals(uid)){
                            final int id= Integer.parseInt(i.child("studentId").getValue().toString());
                            System.out.println("ID"+id);
                            issued.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot j:dataSnapshot.getChildren()){
                                        if(j.child("studentID").getValue().toString().equals(String.valueOf(id))){
                                            System.out.println("IIIII"+j.child("studentID").getValue().toString());
                                            bookID.add(Integer.parseInt(j.child("bookID").getValue().toString()));
                                            issueDate.add(j.child("issueDate").getValue().toString());
                                            renewCnt.add(Integer.parseInt(j.child("renewCount").getValue().toString()));
                                            returnDate.add(j.child("returnDate").getValue().toString());
                                        }
                                    }
                                    bkissued.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot i:dataSnapshot.getChildren()){
                                                for(int l=0;l<bookID.size();l++){
                                                    System.out.println("BookID"+i.child("bookId").getValue().toString());
                                                    System.out.println("ididid"+bookID.get(l));
                                                    System.out.println(i.child("bookId").getValue().toString().equals(String.valueOf(bookID.get(l))));
                                                    if(i.child("bookId").getValue().toString().equals(String.valueOf(bookID.get(l)))){
                                                        bkname.add(i.child("bookName").getValue().toString());
                                                        System.out.println(i.child("bookName").getValue().toString());
                                                        authname.add(i.child("bookAuthor").getValue().toString());
                                                    }
                                                }

                                                String res="";
                                                final int len = bkname.size();
                                                System.out.println(len);
                                                for(int k=0;k<len;k++){
                                                    res+="Book Name :"+bkname.get(k)+"\nBook Author :"+authname.get(k)+"\nIssue Date :"+issueDate.get(k)+"\nReturn Date"+returnDate.get(k)+"\nRenew Count : "+renewCnt.get(k)+"\n\n";
                                                }
                                                t.setText(res);
                                                t.setTextSize(25);
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
