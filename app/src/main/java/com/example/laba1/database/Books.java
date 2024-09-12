package com.example.laba1.database;

import java.io.Serializable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Books implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    public int id;
    @ColumnInfo(name = "Author")
    String Author;
    @ColumnInfo(name = "Genre")
    String Genre;
    @ColumnInfo(name = "Name")
    String Name;
    @ColumnInfo(name = "PublicationDate")
    String PublicationDate;
    @ColumnInfo(name = "rating")
    String rating;
    public Books( String Author, String Genre, String Name,String PublicationDate, String rating) {
        this.id = 0;
        this.Name = Name;
        this.Author = Author;
        this.Genre = Genre;
        this.rating = rating;
        this.PublicationDate = PublicationDate;
    }

    public int getId() {
        return id;
    }
    public String getPublicationDate() {
        return PublicationDate;
    }

    public String getNameBook() {
        return Name;
    }

    public String getNameAuthor() {
        return Author;
    }

    public String getGenre() {
        return Genre;
    }

    public String getRating() {
        return rating;
    }
    public String toString() {
        return "Books{" +
                "NameBook='" + Name + '\'' +
                ", NameAuthor='" + Author + '\'' +
                ", Genre='" + Genre + '\'' +
                ", Rating=" + rating +
                ", Year='" + PublicationDate + '\'' +
                '}';
    }
}
