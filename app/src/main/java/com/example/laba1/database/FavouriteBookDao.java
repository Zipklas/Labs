package com.example.laba1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavouriteBookDao {
    @Insert
    void addFavouriteBook(FavouriteBook book);
    @Update
    void updateFavouriteBook(FavouriteBook book);

    @Delete
    void deleteFavouriteBook(FavouriteBook book);

    @Query("INSERT INTO favourite_books(user_id_favourite, book_id_favourite)  SELECT user_id, book_id from users inner join books where users.user_id = :user_id and books.name = :nameBook")
    void addFavouriteBook(int user_id, String nameBook);

    @Query("DELETE FROM favourite_books WHERE user_id_favourite = (SELECT user_id from users where users.user_id = :user_id) AND book_id_favourite = (SELECT book_id from books where books.name = :nameBook)")
    void deleteFavouriteBook(int user_id, String nameBook);

    @Query("SELECT * FROM favourite_books")
    List<FavouriteBook> getAllItems();
    @Query("SELECT books.book_id,books.author,books.genre,books.name,books.publicationDate,books.rating from users inner join favourite_books on user_id=user_id_favourite inner join books on book_id_favourite=book_id where users.user_id =:user_id")
    List<Books> getFavouriteBookUser(int user_id);
    @Query("SELECT books.book_id,books.author,books.genre,books.name,books.publicationDate,books.rating from users inner join favourite_books on user_id=user_id_favourite inner join books on book_id_favourite=book_id where users.user_id =:user_id and books.name=:nameBook")
    Books getFavouriteBookUser(int user_id, String nameBook);
}
