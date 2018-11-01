package com.example.android.foodtinder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        if(retrofit==null){
            this.retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.food2fork.com/api")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public FoodService getFoodService(){
        return retrofit.create(FoodService.class);
    }
}
