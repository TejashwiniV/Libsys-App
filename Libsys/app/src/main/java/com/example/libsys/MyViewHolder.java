package com.example.libsys;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView t1,t2,t3,t4,t5;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        t1=itemView.findViewById(R.id.textView1);
        t2=itemView.findViewById(R.id.textView2);
        t3=itemView.findViewById(R.id.textView3);
        t4=itemView.findViewById(R.id.textView4);
        t5=itemView.findViewById(R.id.textView5);

    }
}
