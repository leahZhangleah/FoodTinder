package com.example.android.foodtinder.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodService {
    @GET("search")
    Call<RecipeSearchResponse> searchRecipes(@Query("key") String apiKey,
                                             @Query("sort") String sort,
                                             @Query("page") int page);

}
