package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference viewBook;
    String email,profile_id;
    DatabaseReference viewProfile;
    int count;

    FirebaseRecyclerOptions<AddBook1> options;
    FirebaseRecyclerAdapter<AddBook1, MyViewHolder> adapt;

    FirebaseAuth myFirebaseAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        viewProfile = FirebaseDatabase.getInstance().getReference("students");
        viewProfile.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = 0;
                for(DataSnapshot i:dataSnapshot.getChildren()) {
                    String id= i.child("studentId").getValue().toString();
                    if (i.child("userEmail").getValue().toString().equals(email)) {
                        profile_id=id;
                        break;
                    }
                    count++;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        viewBook = FirebaseDatabase.getInstance().getReference("books");
        viewBook.keepSynced(true);
        recyclerView=findViewById(R.id.bookRecyclerView);
        recyclerView.setHasFixedSize(true);
        showData();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        options=new FirebaseRecyclerOptions.Builder<AddBook1>()
                .setQuery(viewBook,AddBook1.class)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.student_menu,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                Intent i = new Intent(StudentActivity.this, LoginActivity.class);
                startActivity(i);
                return true;
            case R.id.renew:
                Intent i2 = new Intent(StudentActivity.this, RenewBook.class);
                startActivity(i2);
                return true;

            case R.id.borrowed:
                Intent i3 = new Intent(StudentActivity.this, ViewBorrowed.class);
                startActivity(i3);
                return true;
            case R.id.profile:
                showMessage("Id:"+profile_id+'\n'+"Email: "+email);
                //Toast.makeText(StudentActivity.this,"Id:"+profile_id+'\n'+"Email: "+email,Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showData() {
        options = new FirebaseRecyclerOptions.Builder<AddBook1>().setQuery(viewBook,AddBook1.class).build();
        adapt = new FirebaseRecyclerAdapter<AddBook1, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull AddBook1 addBook1) {
                myViewHolder.t1.setText("BOOK NAME : "+addBook1.getBookName());
                myViewHolder.t2.setText("AUTHOR : "+addBook1.getBookAuthor());
                myViewHolder.t3.setText("BOOK ID : "+addBook1.getBookId());
                myViewHolder.t4.setText("TOTAL BOOKS : "+addBook1.getBookCount());
                myViewHolder.t5.setText("REMAINING BOOKS : "+addBook1.getRemBookCount());

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
                return new MyViewHolder(view);
            }
        };
        adapt.startListening();
        recyclerView.setAdapter(adapt);
    }

    public void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.show();
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            for(UserInfo profile: user.getProviderData()){
                email = profile.getEmail();
                System.out.println(email);
            }
        }
    }
}
