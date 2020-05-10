package com.example.myprogrammationmobileproject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FinancialModelingPrepAPI {
    @GET("/api/v3/company/stock/list")
    Call<RestFinanceResponse> getFMPResponse();
}
