package com.example.demopush.API;

import com.example.demopush.Login.LoginRequest;
import com.example.demopush.Login.LoginResponse;
import com.example.demopush.Register.RegisterRequest;
import com.example.demopush.Register.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

}
