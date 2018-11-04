package com.example.android.foodtinder;

import android.util.Log;

import com.example.android.foodtinder.db.Recipe;
import com.example.android.foodtinder.retrofit.RecipeSearchResponse;
import com.example.android.foodtinder.retrofit.RetrofitClient;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {
    private static final String TAG = "RecipeRepository";
    RetrofitClient retrofitClient;
    private String API_KEY;
    public static final String sort = "r";
    //todo

    public RecipeRepository(String API_KEY) {
        this.retrofitClient = new RetrofitClient();
        this.API_KEY = API_KEY;
    }

    public void getNextRecipe(int nextPage){
        retrofitClient.getFoodService().searchRecipes(API_KEY,sort,nextPage)
                .enqueue(new Callback<RecipeSearchResponse>() {
                    @Override
                    public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                        if(response.isSuccessful()){
                            RecipeSearchResponse searchResponse = response.body();
                            Recipe recipe = searchResponse.getFirstRecipe();
                            Log.d(TAG,"id: "+recipe.getRecipe_id()+"title: "+recipe.getTitle()+
                                    "imageurl: "+recipe.getImage_url()+"sourceurl: "+recipe.getSource_url()
                                    +"favorite: "+recipe.getFavorite());
                            RecipeEvent recipeEvent = new RecipeEvent(recipe,null,null);
                            EventBus.getDefault().post(recipeEvent);
                        }else{
                            String responseMsg = response.message();
                            RecipeEvent recipeEvent = new RecipeEvent(null,responseMsg,null);
                            EventBus.getDefault().post(recipeEvent);
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                        RecipeEvent recipeEvent = new RecipeEvent(null,null,t.getMessage());
                        EventBus.getDefault().post(recipeEvent);
                    }
                });

    }
}
