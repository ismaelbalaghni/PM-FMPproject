package com.example.myprogrammationmobileproject.presentation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myprogrammationmobileproject.Constantes;
import com.example.myprogrammationmobileproject.Injection;
import com.example.myprogrammationmobileproject.R;
import com.example.myprogrammationmobileproject.presentation.model.StockCompany;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView companyName;
    private TextView companySymbol;
    private ImageView companyLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        companyName = findViewById(R.id.companyNameDetail);
        companySymbol = findViewById(R.id.companySymbolDetail);
        companyLogo = findViewById(R.id.companyLogoDetail);
        Intent intent = getIntent();
        String company = intent.getStringExtra(Constantes.INTENT_COMPANY_KEY);
        StockCompany stockCompany = Injection.getGson().fromJson(company, StockCompany.class);
        showStockCompanyDetail(stockCompany);
    }

    private void showStockCompanyDetail(StockCompany stockCompany) {
        companyName.setText(stockCompany.getName());
        companySymbol.setText(stockCompany.getSymbol());
        Picasso.with(getApplicationContext()).load(Constantes.PLACEHOLDER_IMAGE).into(companyLogo);

    }
}
