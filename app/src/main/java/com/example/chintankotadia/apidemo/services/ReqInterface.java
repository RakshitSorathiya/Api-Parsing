package com.example.chintankotadia.apidemo.services;

import com.example.chintankotadia.apidemo.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by chintankotadia on 11/01/18.
 */

public interface ReqInterface {

    @GET("/posts")
    Call<List<Example>> getMyJSON();

}

