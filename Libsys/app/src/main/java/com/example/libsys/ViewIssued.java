package com.example.libsys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewIssued extends AppCompatActivity {
    RecyclerView IssuedView;
    DatabaseReference viewIssued;
    FirebaseRecyclerOptions<IssueBook1> options2;
    FirebaseRecyclerAdapter<IssueBook1, IssuedHolder> adapt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_issued);

        viewIssued = FirebaseDatabase.getInstance().getReference("issue");
        viewIssued.keepSynced(true);

        IssuedView=findViewById(R.id.issuedRecyclerView);
        IssuedView.setHasFixedSize(true);

        IssuedView.setLayoutManager(new LinearLayoutManager(this));
        showData();
    }

    private void showData() {
        options2=new FirebaseRecyclerOptions.Builder<IssueBook1>()
                .setQuery(viewIssued,IssueBook1.class)
                .build();

        adapt2 = new FirebaseRecyclerAdapter<IssueBook1, IssuedHolder>(options2) {
            @NonNull
            @Override
            public IssuedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewissued,parent,false);
                return new IssuedHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull IssuedHolder issuedHolder, int i, @NonNull IssueBook1 issueBook1) {
                if(!issueBook1.getIssueDate().equals("")) {
                    issuedHolder.t1.setText("BOOK ID : " + issueBook1.getBookID());
                    issuedHolder.t2.setText("STUDENT ID : " + issueBook1.getStudentID());
                    issuedHolder.t3.setText("ISSUE DATE : " + issueBook1.getIssueDate());
                    issuedHolder.t4.setText("RETURN DATE : " + issueBook1.getReturnDate());
                    issuedHolder.t5.setText("RENEWAL COUNT : " + issueBook1.getRenewCount());
                }

            }
        };
        adapt2.startListening();
        IssuedView.setAdapter(adapt2);
    }
}

