package com.example.laba1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laba1.database.Books;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<booksHolder> {
    public interface OnItemClickListener {
        void onItemClick(Books myItem);
        void onItemLongClick(Books myItem);
    }
    private OnItemClickListener myItemListener;
    private Context context;
    private List<Books> books;

    public adapter(Context context, List<Books> books, OnItemClickListener myItemListener) {
        this.context = context;
        this.books = books;
        this.myItemListener = myItemListener;
    }

    @NonNull
    @Override
    public booksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new booksHolder(LayoutInflater.from(context).inflate(R.layout.itemlayout, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull booksHolder holder, int position) {
        holder.namebooks.setText(books.get(position).getNameBook());
        holder.authorsname.setText(books.get(position).getNameAuthor());

        holder.itemView.setOnClickListener(view->{
            myItemListener.onItemClick(books.get(position));
        });
        holder.itemView.setOnLongClickListener(view->{
            myItemListener.onItemLongClick(books.get(position));
            return true;
        });
    }
    @Override
    public int getItemCount() {
        return books.size();
    }
}
