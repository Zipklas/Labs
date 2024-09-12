package com.example.laba1.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.laba1.database.Books;
import com.example.laba1.database.BooksDao;
import com.example.laba1.database.FavouriteBook;
import com.example.laba1.database.FavouriteBookDao;
import com.example.laba1.database.User;
import com.example.laba1.database.UserDao;

@Database(entities = {User.class, Books.class, FavouriteBook.class},version = 1)
public abstract class Db extends RoomDatabase {
    public abstract UserDao getUserDao();
    public abstract BooksDao getBookDao();
    public abstract FavouriteBookDao getFavouriteBookDao();

}