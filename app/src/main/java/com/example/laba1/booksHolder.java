package com.example.laba1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class booksHolder extends RecyclerView.ViewHolder  {
    TextView namebooks;
    TextView authorsname;


    public booksHolder(@NonNull View itemView) {
        super(itemView);
        namebooks = itemView.findViewById(R.id.nameBook);
        authorsname = itemView.findViewById(R.id.nameAuthor);
    }

}
