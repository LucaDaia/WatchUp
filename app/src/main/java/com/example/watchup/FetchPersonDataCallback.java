package com.example.watchup;

import java.util.List;

public interface FetchPersonDataCallback {
    void onPersonSuccess(List<Person> personList);
    void onPersonFailure(String errorMessage);
}
