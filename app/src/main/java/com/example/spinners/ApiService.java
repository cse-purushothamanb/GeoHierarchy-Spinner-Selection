package com.example.spinners;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("Select")
    Call<List<District>> get_details(@Body GenericModelClass latlngmodel);
    @Headers("Content-Type: application/json")
    @POST("Select")
    Call<List<Taluk>> get_taluk_details(@Body GenericModelClass latlngmodel);

    @Headers("Content-Type: application/json")
    @POST("Select")
    Call<List<Hobli>> get_Hobli_details(@Body GenericModelClass latlngmodel);

    @Headers("Content-Type: application/json")
    @POST("Select")
    Call<List<Village>> get_Village_details(@Body GenericModelClass latlngmodel);
    @Headers("Content-Type: application/json")
    @POST("Select")
    Call<List<Survey>> get_Survey_details(@Body GenericModelClass latlngmodel);
}
