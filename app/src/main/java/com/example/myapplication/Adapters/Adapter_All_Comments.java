package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ResComment;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Adapter_All_Comments extends RecyclerView.Adapter<Adapter_All_Comments.Viewholder> {

    private final Context c;
    private ArrayList<ResComment> commentList;

    public Adapter_All_Comments(Context c, ArrayList<ResComment> commentList) {
        this.c = c;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public Adapter_All_Comments.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.rv_product_comment_section, parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        public Viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
