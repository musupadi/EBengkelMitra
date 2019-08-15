package com.destiny.e_bengkelmitra.API;

import com.destiny.e_bengkelmitra.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("LoginMitra.php")
    Call<ResponseModel> login(@Field("username") String username,
                              @Field("password") String password);
}
