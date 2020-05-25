package com.example.myprogrammationmobileproject.presentation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myprogrammationmobileproject.Constantes;
import com.example.myprogrammationmobileproject.Injection;
import com.example.myprogrammationmobileproject.R;
import com.example.myprogrammationmobileproject.presentation.model.FullStockCompany;
import com.example.myprogrammationmobileproject.presentation.model.StockCompany;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView companyName;
    private TextView companySymbol;
    private TextView companyExchange;
    private TextView companyPrice;
    private TextView companyWebsite;
    private TextView companyDescription;
    private ImageView companyLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        companyName = findViewById(R.id.companyNameDetail);
        companySymbol = findViewById(R.id.companySymbolDetail);
        companyLogo = findViewById(R.id.companyLogoDetail);
        companyExchange = findViewById(R.id.companyExchangeDetail);
        companyPrice = findViewById(R.id.companyPriceDetail);
        companyWebsite = findViewById(R.id.companyWebsiteDetail);
        companyDescription = findViewById(R.id.companyDescriptionDetail);
        Intent intent = getIntent();
        String company = intent.getStringExtra(Constantes.INTENT_COMPANY_KEY);
        StockCompany stockCompany = Injection.getGson().fromJson(company, StockCompany.class);
        Log.d("test test test", stockCompany.getSymbol());
        Log.d("test test test", "calling the API");
        Log.d("test test test", Constantes.API_KEY);
        makeAPIcall(stockCompany);
        Log.d("test test test", "API called");
    }

    Transformation transformation = new Transformation() {
        @Override
        public Bitmap transform(Bitmap source) {
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetWidth = source.getWidth() * 3;
            int targetHeight = (int) (targetWidth*aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if(result != source){
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "transformation with desidered width";
        }
    };
    private void showStockCompanyDetail(FullStockCompany stockCompany) {
        companyName.setText(stockCompany.getCompanyName());
        companySymbol.setText(stockCompany.getSymbol());
        companyDescription.setText(stockCompany.getDescription().split("\\.")[0]);
        companyWebsite.setText(stockCompany.getWebsite());
        companyExchange.setText(stockCompany.getExchange());
        companyPrice.setText(String.valueOf(stockCompany.getPrice()));
        Picasso.with(getApplicationContext()).load(stockCompany.getImage()).transform(transformation).into(companyLogo);

    }

    private void makeAPIcall(StockCompany company){
        Log.d("test test test", "in the API call");
        Call<List<FullStockCompany>> call = Injection.getFinancialModelingPrepAPI().getStockCompanyDetails(company.getSymbol(), Constantes.API_KEY);
        Log.d("test test test", "we will enqueue the call");
        Log.d("test test test", String.valueOf(call.request()));
        call.enqueue(fullStockCompanyCallback);
    }

    Callback<List<FullStockCompany>> fullStockCompanyCallback = new Callback<List<FullStockCompany>>() {
        @Override
        public void onResponse(Call<List<FullStockCompany>> call, Response<List<FullStockCompany>> response) {
            if(response.isSuccessful() && response.body() != null){
                List<FullStockCompany> fullStockCompany = response.body();
                showStockCompanyDetail(fullStockCompany.get(0));
            } else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<List<FullStockCompany>> call, Throwable t) {
            Toast.makeText(DetailActivity.this, "SEVERE API ERROR", Toast.LENGTH_SHORT).show();
            Log.e("test test test", "SEVERE API ERROR");
            //showError();
        }
    };

    private void showError() {
        Log.d("test test test", "it failed");
        Toast.makeText(this, R.string.toast_error, Toast.LENGTH_SHORT).show();
    }
}
