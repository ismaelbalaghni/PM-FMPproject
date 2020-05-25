package com.example.myprogrammationmobileproject.data;

import com.example.myprogrammationmobileproject.presentation.model.FullStockCompany;
import com.example.myprogrammationmobileproject.presentation.model.StockCompany;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FinancialModelingPrepAPI {

    @GET("/api/v3/stock/list")
    Call<List<StockCompany>> getFMPResponse(@Query("apikey") String apiKey);

    @GET("/api/v3/profile/{symbol}")
    Call<List<FullStockCompany>> getStockCompanyDetails(@Path("symbol") String companySymbol, @Query("apikey") String apiKey);
}
