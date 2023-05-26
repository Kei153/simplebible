package com.example.mybible.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("bible")
    Call<List<BibleVO>> doGetJsonData(
        @Query("lang") String lang,
        @Query("doc") String doc,
        @Query("start") String start,
        @Query("end") String end
    );
}
