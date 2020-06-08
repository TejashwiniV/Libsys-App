package com.example.libsys;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IssuedHolder extends RecyclerView.ViewHolder{
    TextView t1,t2,t3,t4,t5;
    public IssuedHolder(@NonNull View itemView) {
        super(itemView);
        t1=itemView.findViewById(R.id.textIssued1);
        t2=itemView.findViewById(R.id.textIssued2);
        t3=itemView.findViewById(R.id.textIssued3);
        t4=itemView.findViewById(R.id.textIssued4);
        t5=itemView.findViewById(R.id.textIssued5);
    }
}
