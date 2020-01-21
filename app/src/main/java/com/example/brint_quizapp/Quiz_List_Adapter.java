package com.example.brint_quizapp;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Quiz_List_Adapter extends RecyclerView.Adapter<Quiz_List_Adapter.quizListViewHolder> {

    private static View.OnClickListener onItemClickListener;
    private ArrayList<String> quizNames;

    String curr_quizName;

    private Button quiz_Item;

    public static void setClickListener(View.OnClickListener callback) {
        onItemClickListener = callback;
    }

    public static class quizListViewHolder extends RecyclerView.ViewHolder {

        public static View itemView;
        public Button quiz_Item;
        private View.OnClickListener onItemClickListener;


        public quizListViewHolder(@NonNull View itemView) {
            super(itemView);
            quiz_Item = itemView.findViewById(R.id.quiz_item);
        }
    }

    public Quiz_List_Adapter (ArrayList<String> quizNames) {
        this.quizNames = quizNames;
    }

    @NonNull
    @Override
    public quizListViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_list_item, parent, false);
        quizListViewHolder quizHolder = new quizListViewHolder(v);
        quizListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(view);
            }
        });
        return quizHolder;
    }

    @Override
    public void onBindViewHolder (@NonNull quizListViewHolder holder, int position) {
        curr_quizName = quizNames.get(position);

        holder.quiz_Item.setText(curr_quizName);
    }

    @Override
    public int getItemCount() {
        return quizNames.size();
    }


}

