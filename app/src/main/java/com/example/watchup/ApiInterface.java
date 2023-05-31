package com.example.watchup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("images")
    Call<List<Image>> getImages();

    @GET("persons")
    Call<List<Person>> getPersons();
}
