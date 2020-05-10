package com.example.myprogrammationmobileproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewAnimator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://financialmodelingprep.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> input = new ArrayList<>();
        makeAPIcall();
    }

    private void createList(ArrayList<StockCompany> input) {
        recyclerView = findViewById(R.id.indexes);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // define an adapter
        mAdapter = new StockAdapter(this, input);
        recyclerView.setAdapter(mAdapter);
    }

    private void makeAPIcall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FinancialModelingPrepAPI financialAPI = retrofit.create(FinancialModelingPrepAPI.class);

        Call<RestFinanceResponse> call = financialAPI.getFMPResponse();
        call.enqueue(new Callback<RestFinanceResponse>() {

            @Override
            public void onResponse(Call<RestFinanceResponse> call, Response<RestFinanceResponse> response) {

                if(response.isSuccessful() && response.body() != null){
                    ArrayList<StockCompany> stockCompanies = response.body().getSymbolsList();
                    Toast.makeText(getApplicationContext(), "Données récupérées.", Toast.LENGTH_SHORT).show();
                    createList(stockCompanies);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestFinanceResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "Erreur API.", Toast.LENGTH_SHORT).show();
    }

}
