package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {
//    RecyclerView recyclerView;
//    DatabaseReference viewBook;
//    FirebaseRecyclerOptions<AddBook1> options;
//    FirebaseRecyclerAdapter<AddBook1, MyViewHolder> adapt;
    //Button logOut,addBook1,issueBooks1,renewBooks1,returnBooks1,viewBooks1,viewUsers1,viewissueBooks1;

//    FirebaseAuth myFirebaseAuth;
//    private FirebaseAuth.AuthStateListener myAuthStateListener;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
//        myFirebaseAuth=FirebaseAuth.getInstance();

//        logOut=findViewById(R.id.logout);
//        addBook1 = findViewById(R.id.addBooks);
//        issueBooks1 = findViewById(R.id.issueBooks);
//        renewBooks1=findViewById(R.id.BookRenewal);
//        returnBooks1=findViewById(R.id.ReturnBooks);
//        viewBooks1=findViewById(R.id.viewBooks);
//        viewUsers1=findViewById(R.id.viewUsers);
//        viewissueBooks1=findViewById(R.id.viewissueBooks);
//
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent i=new Intent(AdminActivity.this,LoginActivity.class);
//                startActivity(i);
//            }
//        });
//
//        addBook1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(AdminActivity.this,AddBook.class);
//                startActivity(i);
//            }
//        });
//
//        issueBooks1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(AdminActivity.this,IssueBook.class);
//                startActivity(i);
//            }
//        });
//        renewBooks1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(AdminActivity.this,RenewBook.class);
//                startActivity(i);
//            }
//        });
//        returnBooks1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(AdminActivity.this,ReturnBook.class);
//                startActivity(i);
//            }
//        });
//        viewBooks1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(AdminActivity.this,ViewBook.class);
//                startActivity(i);
//            }
//        });
//        viewUsers1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(AdminActivity.this,ViewStudent.class);
//                startActivity(i);
//            }
//        });
//        viewissueBooks1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(AdminActivity.this,ViewIssued.class);
//                startActivity(i);
//            }
//        });
//        viewBook = FirebaseDatabase.getInstance().getReference("books");
//        viewBook.keepSynced(true);
//        recyclerView=findViewById(R.id.bookRecyclerView);
        //recyclerView.setHasFixedSize(true);
        //showData();

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        options=new FirebaseRecyclerOptions.Builder<AddBook1>()
//                .setQuery(viewBook,AddBook1.class)
//                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.view_students:
                Intent i = new Intent(AdminActivity.this, ViewStudent.class);
                startActivity(i);
                return true;
            case R.id.view_books:
                Intent i7 = new Intent(AdminActivity.this, ViewBook.class);
                startActivity(i7);
                return true;
            case R.id.add_books:
                Intent i2 = new Intent(AdminActivity.this, AddBook.class);
                startActivity(i2);
                return true;
            case R.id.issue_book:
                Intent i3 = new Intent(AdminActivity.this, IssueBook.class);
                startActivity(i3);
                return true;
            case R.id.view_issue:
                Intent i4 = new Intent(AdminActivity.this, ViewIssued.class);
                startActivity(i4);
                return true;
            case R.id.return_book:
                Intent i5 = new Intent(AdminActivity.this, ReturnBook.class);
                startActivity(i5);
                return true;
            case R.id.logout1:
                Intent i6 = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(i6);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//    private void showData() {
//        options = new FirebaseRecyclerOptions.Builder<AddBook1>().setQuery(viewBook,AddBook1.class).build();
//        adapt = new FirebaseRecyclerAdapter<AddBook1, MyViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull AddBook1 addBook1) {
//                myViewHolder.t1.setText("BOOK NAME : "+addBook1.getBookName());
//                myViewHolder.t2.setText("AUTHOR : "+addBook1.getBookAuthor());
//                myViewHolder.t3.setText("BOOK ID : "+addBook1.getBookId());
//                myViewHolder.t4.setText("TOTAL BOOKS : "+addBook1.getBookCount());
//                myViewHolder.t5.setText("REMAINING BOOKS : "+addBook1.getRemBookCount());
//
//            }
//
//            @NonNull
//            @Override
//            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
//                return new MyViewHolder(view);
//            }
//        };
//        adapt.startListening();
//        recyclerView.setAdapter(adapt);
//    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        if(myFirebaseAuth.getCurrentUser()==null){
//            finish();
//            Intent i=new Intent(AdminActivity.this,LoginActivity.class);
//            startActivity(i);
//        }
//    }
}
