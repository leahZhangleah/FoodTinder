package com.example.android.foodtinder.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit;

    public RetrofitClient() {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.food2fork.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

    }

    public FoodService getFoodService(){
        return retrofit.create(FoodService.class);
    }
}
