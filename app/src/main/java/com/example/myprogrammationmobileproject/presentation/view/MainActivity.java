package com.example.myprogrammationmobileproject.presentation.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprogrammationmobileproject.Constantes;
import com.example.myprogrammationmobileproject.R;
import com.example.myprogrammationmobileproject.data.FinancialModelingPrepAPI;
import com.example.myprogrammationmobileproject.presentation.model.StockCompany;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements StockAdapter.StockAdapterListener {

    private RecyclerView recyclerView;
    private StockAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        gson = new GsonBuilder()
                .setLenient()
                .create();
        setSupportActionBar(toolbar);
        List<StockCompany> stockCompanyList = getDataFromCache();
        if(stockCompanyList != null){
            createList(stockCompanyList);
        } else {
            makeAPIcall();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh_button) {
            makeAPIcall();
        } else if(id == R.id.app_bar_search){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }


    private void createList(List<StockCompany> input) {
        // TODO CORRIGER L'ERREUR LOGCAT DE L'ADAPTATER
        recyclerView = findViewById(R.id.indexes);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        // define an adapter
        mAdapter = new StockAdapter(this, input, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void makeAPIcall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FinancialModelingPrepAPI financialAPI = retrofit.create(FinancialModelingPrepAPI.class);

        Call<List<StockCompany>> call = financialAPI.getFMPResponse(Constantes.API_KEY);
        call.enqueue(new Callback<List<StockCompany>>() {

            @Override
            public void onResponse(Call<List<StockCompany>> call, Response<List<StockCompany>> response) {

                if(response.isSuccessful() && response.body() != null){
                    List<StockCompany> fullStockCompanies = response.body();
                    Toast.makeText(getApplicationContext(), R.string.toast_success, Toast.LENGTH_SHORT).show();
                    List<StockCompany> stockCompanies = fullStockCompanies.subList(0, fullStockCompanies.size()/20);
                    saveList(stockCompanies);
                    createList(stockCompanies);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<StockCompany>> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(List<StockCompany> stockCompanies) {
        String stockCompaniesAsJson = gson.toJson(stockCompanies);
        sharedPreferences.edit().putString(Constantes.FMP_COMPANIES_KEY,stockCompaniesAsJson).apply();
        Toast.makeText(this, R.string.shared_pref_saved_ok, Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), R.string.toast_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelected(StockCompany selectedCompany) {
        Toast.makeText(this, "Selected: " + selectedCompany, Toast.LENGTH_SHORT).show();
    }
}
