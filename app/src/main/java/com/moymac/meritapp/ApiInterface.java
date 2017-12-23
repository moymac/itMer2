package com.moymac.meritapp;

import com.moymac.meritapp.Models.Categories;
import com.moymac.meritapp.Models.Inputs;
import com.moymac.meritapp.Models.Projects;
import com.moymac.meritapp.Models.Steps;
import com.moymac.meritapp.Models.Templates;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by moymac on 11/14/17.
 */

public interface ApiInterface {

    String BASE_URL = "https://serene-brook-71867.herokuapp.com/";
//    String BASE_URL = "http://192.168.100.33:8000/";


    @GET("categories")
    Call<List<Categories>> getCategories();

    @GET("templates/")
    Call<List<Templates>> getTemplates(@Query("category") int category_id);

    @GET("steps/")
    Call<List<Steps>> getSteps(@Query("template") int template_id);


    @GET("projects")
    Call<List<Projects>> getProjects();

    @GET("inputs/")
    Call<List<Inputs>> getInputs(@Query("userID") int user_id);


    @FormUrlEncoded
    @POST("inputs/")
    Call<Inputs> postInputs(@Field("step") String stepNum, @Field("text") String textData);


}
