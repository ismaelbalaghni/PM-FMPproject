package com.ismaelbalaghni.fmpproject.presentation.controller;

import android.content.SharedPreferences;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismaelbalaghni.fmpproject.Constantes;
import com.ismaelbalaghni.fmpproject.Injection;
import com.ismaelbalaghni.fmpproject.R;
import com.ismaelbalaghni.fmpproject.presentation.model.StockCompany;
import com.ismaelbalaghni.fmpproject.presentation.view.MainActivity;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity view, Gson gson, SharedPreferences sharedPreferences) {
        this.view = view;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){
        List<StockCompany> stockCompanyList = getDataFromCache();
        if(stockCompanyList != null){
            view.showList(stockCompanyList);
        } else {
            makeAPIcall();
        }
    }

    public void onItemClick(StockCompany stockCompany){
        view.showDetails(stockCompany);
    }

    public boolean onButtonClick(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.refresh_button){
            makeAPIcall();
            return true;
        } else if(id==R.id.app_bar_search){
            return true;
        } else if(id==R.id.dark_mode_toggle){
            toggleDarkMode();
        }
        return view.getParent().onContextItemSelected(item);
    }

    private void toggleDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public void handleBack(){
        if(view.searchView.isIconified()){
            view.searchView.setIconified(true);
            return;
        }
    }

    private List<StockCompany> getDataFromCache() {
        String companiesAsJSON = sharedPreferences.getString(Constantes.FMP_COMPANIES_KEY, null);
        Type listType = new TypeToken<List<StockCompany>>(){}.getType();
        if(companiesAsJSON == null){
            return null;
        } else {
            return gson.fromJson(companiesAsJSON, listType);
        }
    }

    private void makeAPIcall(){
        Call<List<StockCompany>> call = Injection.getFinancialModelingPrepAPI().getFMPResponse(Constantes.API_KEY);
        call.enqueue(new Callback<List<StockCompany>>() {
            @Override
            public void onResponse(Call<List<StockCompany>> call, Response<List<StockCompany>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<StockCompany> fullStockCompanies = response.body();
                    List<StockCompany> stockCompanies = fullStockCompanies.subList(0, fullStockCompanies.size()/2);
                    saveList(stockCompanies);
                    view.showList(stockCompanies);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<List<StockCompany>> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<StockCompany> stockCompanies) {
        String stockCompaniesAsJson = gson.toJson(stockCompanies);
        sharedPreferences.edit().putString(Constantes.FMP_COMPANIES_KEY,stockCompaniesAsJson).apply();
    }
}
