package com.example.myprogrammationmobileproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FinancialModelingPrepAPI {
    @GET("/api/v3/stock/list")
    Call<List<StockCompany>> getFMPResponse(@Query("apikey") String apiKey);
}
