package com.example.android.foodtinder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodService {
    @GET("/search")
    Call<List<RecipeSearchResponse>> searchRecipes(@Query("q") String query,
                                                   @Query("key") String apiKey,
                                                   @Query("sort") int sort,
                                                   @Query("page") int page);
}
