package com.irfandevandroid.loginapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {
    @POST("authentication/api/token/")
    Call<Token> login(@Body Login login);
}
