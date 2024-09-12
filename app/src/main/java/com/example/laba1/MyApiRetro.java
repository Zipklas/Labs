package com.example.laba1;

import com.example.laba1.database.Books;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApiRetro {
    //https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/Books2022.json

    @GET("Books2022.json")
    Call<List<Books>>getBooks();
}
