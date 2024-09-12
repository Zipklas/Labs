package com.example.laba1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface BooksDao {
    @Insert
    public void addBook(Books book);
    @Update
    public void updateBook(Books book);
    @Delete
    public void deleteBook(Books book);
    @Query("SELECT * FROM books")
    public List<Books> getAllBook();
    @Query("SELECT count(*) FROM books")
    public int getCountBook();
    @Query("SELECT * FROM books WHERE name == :Name AND author ==:Author")
    public Books getBook(String Name, String Author);
}
