package com.moymac.meritapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moymac on 11/14/17.
 */

public class ApiClient {

    public static final String BASE_URL = "https://serene-brook-71867.herokuapp.com/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }


}
