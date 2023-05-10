package com.example.watchup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendFetcher {
    private ApiInterface apiInterface;
    private List<Image> imageList;

    public BackendFetcher() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    // Fetch data method
    public void fetchData() {
        Call<List<Image>> call = apiInterface.getImages();
        call.enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (response.isSuccessful()) {
                    imageList = response.body();
                    System.out.println("a ajuns pana aici");
                    System.out.println(imageList);
                    System.out.println(response.body());
                } else {
                    System.out.println("Response wasn't succesful");
                }
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                throw  new RuntimeException("Failed to get images!");
            }
        });
    }

    public List<Image> getImageList() {
        return imageList;
    }
}
