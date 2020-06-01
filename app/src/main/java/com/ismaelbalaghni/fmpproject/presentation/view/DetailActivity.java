package com.ismaelbalaghni.fmpproject.presentation.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ismaelbalaghni.fmpproject.Constantes;
import com.ismaelbalaghni.fmpproject.Injection;
import com.ismaelbalaghni.fmpproject.R;
import com.ismaelbalaghni.fmpproject.presentation.model.FullStockCompany;
import com.ismaelbalaghni.fmpproject.presentation.model.StockCompany;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
        makeAPIcall(stockCompany);
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
        Call<List<FullStockCompany>> call = Injection.getFinancialModelingPrepAPI().getStockCompanyDetails(company.getSymbol(), Constantes.API_KEY);
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
            showError();
        }
    };

    private void showError() {
        Toast.makeText(this, R.string.toast_error, Toast.LENGTH_SHORT).show();
        Log.e("PM-FMPproject", "SEVERE API ERROR");
    }
}
