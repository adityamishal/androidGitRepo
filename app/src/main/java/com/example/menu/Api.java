package com.example.menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api  {

    String URL= "http://piyushn88.pythonanywhere.com/categoryrest/v1/";

    @GET("interviews")
    Call<List<Data>> getData();
}
