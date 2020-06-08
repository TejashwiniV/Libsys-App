package com.example.libsys;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentHolder extends RecyclerView.ViewHolder {
    TextView t1,t2,t3;
    public StudentHolder(@NonNull View itemView) {
        super(itemView);
        t1=itemView.findViewById(R.id.textStd1);
        t2=itemView.findViewById(R.id.textStd2);
        t3=itemView.findViewById(R.id.textStd3);
    }
}
