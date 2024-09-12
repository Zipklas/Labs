package com.example.laba1.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_books", foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "user_id",
        childColumns = "user_id_favourite",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Books.class,
                parentColumns = "book_id",
                childColumns = "book_id_favourite",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)
})
public class FavouriteBook {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pm_favourite_book_id")
    int favourite_book_id;
    @ColumnInfo(name = "user_id_favourite")
    int userIdFavourite;
    @ColumnInfo(name = "book_id_favourite")
    int bookIdFavourite;


    public FavouriteBook(int favourite_book_id, int userIdFavourite, int bookIdFavourite) {
        this.favourite_book_id = favourite_book_id;
        this.userIdFavourite = userIdFavourite;
        this.bookIdFavourite = bookIdFavourite;
    }

    public int getFavourite_book_id() {
        return favourite_book_id;
    }

    public int getUserIdFavourite() {
        return userIdFavourite;
    }

    public int getBookIdFavourite() {
        return bookIdFavourite;
    }
}
