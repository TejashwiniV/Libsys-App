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

public class ViewStudent extends AppCompatActivity {
    RecyclerView StudentView;
    DatabaseReference viewSTD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        viewSTD = FirebaseDatabase.getInstance().getReference("students");
        viewSTD.keepSynced(true);

        StudentView=findViewById(R.id.stdRecyclerView);
        StudentView.setHasFixedSize(true);

        StudentView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Student1> options1=new FirebaseRecyclerOptions.Builder<Student1>()
                .setQuery(viewSTD,Student1.class)
                .build();

        FirebaseRecyclerAdapter<Student1, StudentHolder> adapt1;
        options1 = new FirebaseRecyclerOptions.Builder<Student1>().setQuery(viewSTD,Student1.class).build();
        adapt1 = new FirebaseRecyclerAdapter<Student1, StudentHolder>(options1) {
            @Override
            protected void onBindViewHolder(@NonNull StudentHolder studentHolder, int i, @NonNull Student1 student1) {
                studentHolder.t1.setText("STUDENT NAME : "+student1.getUserEmail());
                studentHolder.t2.setText("STUDENT ID : "+student1.getStudentId() );
                studentHolder.t3.setText("BOOK BORROWED : "+student1.getBorrowedBooks());
            }

            @NonNull
            @Override
            public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewstd,parent,false);
                return new StudentHolder(view);
            }
        };
        adapt1.startListening();
        StudentView.setAdapter(adapt1);
    }
}
