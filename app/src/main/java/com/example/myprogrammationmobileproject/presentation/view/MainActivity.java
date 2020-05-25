package com.example.myprogrammationmobileproject.presentation.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprogrammationmobileproject.Constantes;
import com.example.myprogrammationmobileproject.Injection;
import com.example.myprogrammationmobileproject.R;
import com.example.myprogrammationmobileproject.presentation.controller.MainController;
import com.example.myprogrammationmobileproject.presentation.model.StockCompany;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StockAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public SearchView searchView;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(this,
                Injection.getGson(),
                Injection.getSharedPreferences(getApplicationContext()));
        controller.onStart();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        return controller.onButtonClick(item);
    }

    @Override
    public void onBackPressed() {
        controller.handleBack();
        super.onBackPressed();
    }

    public void showList(List<StockCompany> input) {
        // TODO CORRIGER L'ERREUR LOGCAT DE L'ADAPTATER
        recyclerView = findViewById(R.id.indexes);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        // define an adapter
        mAdapter = new StockAdapter(this, input, new StockAdapter.StockAdapterListener() {
            @Override
            public void onItemClick(StockCompany company) {
                controller.onItemClick(company);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void showError() {
        Toast.makeText(getApplicationContext(), R.string.toast_error, Toast.LENGTH_SHORT).show();
    }

    public void showDetails(StockCompany stockCompany) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constantes.INTENT_COMPANY_KEY, Injection.getGson().toJson(stockCompany));
        startActivity(intent);

    }
}
