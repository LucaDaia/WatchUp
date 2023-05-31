package com.example.watchup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendFetcher {
    private ApiInterface apiInterface;

    public BackendFetcher() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    // Fetch data method
    public void fetchData(FetchDataCallback callback) {
        Call<List<Image>> call = apiInterface.getImages();
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful()) {
                    List<Image> imageList = response.body();
                    callback.onSuccess(imageList);
                } else {
                    callback.onFailure("Response wasn't successful");
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                callback.onFailure("Failed to get images: " + t.getMessage());
            }
        });
    }

    public void fetchPersonData(FetchPersonDataCallback callback1) {
        Call<List<Person>> call = apiInterface.getPersons();
        call.enqueue(new Callback<List<Person>>() {

            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if(response.isSuccessful()) {
                    List<Person> personList = response.body();
                    callback1.onPersonSuccess(personList);
                } else
                {
                    callback1.onPersonFailure("Response wasn't succesfull!");
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                callback1.onPersonFailure("Failed to get images: " + t.getMessage());
            }
        });
    }

}
