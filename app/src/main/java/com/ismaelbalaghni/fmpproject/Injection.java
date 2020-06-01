package com.ismaelbalaghni.fmpproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ismaelbalaghni.fmpproject.data.FinancialModelingPrepAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Injection {
    private static Gson gsonInstance;
    private static FinancialModelingPrepAPI financialModelingPrepAPI;
    private static SharedPreferences sharedPreferences;

    public static Gson getGson(){
        if(gsonInstance ==null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static FinancialModelingPrepAPI getFinancialModelingPrepAPI(){
        if(financialModelingPrepAPI == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            financialModelingPrepAPI = retrofit.create(FinancialModelingPrepAPI.class);
        }
        return financialModelingPrepAPI;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferences==null){
            sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }
}
