package com.example.watchup;

import java.util.List;

public interface FetchDataCallback {
    void onSuccess(List<Image> imageList);
    void onFailure(String errorMessage);
}
