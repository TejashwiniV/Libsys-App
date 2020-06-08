package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewBook extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference viewBook;

    FirebaseRecyclerOptions<AddBook1> options;
    FirebaseRecyclerAdapter<AddBook1, MyViewHolder> adapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
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
                View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
                return new MyViewHolder(view);
            }
        };
        adapt.startListening();
        recyclerView.setAdapter(adapt);
    }
}
