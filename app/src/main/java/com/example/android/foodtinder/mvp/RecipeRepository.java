package com.example.android.foodtinder.mvp;

import android.util.Log;

import com.example.android.foodtinder.AppExecutor;
import com.example.android.foodtinder.db.FoodDatabase;
import com.example.android.foodtinder.db.Recipe;
import com.example.android.foodtinder.eventbus.RecipeEvent;
import com.example.android.foodtinder.retrofit.RecipeSearchResponse;
import com.example.android.foodtinder.retrofit.RetrofitClient;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {
    private static final String TAG = "RecipeRepository";
    RetrofitClient retrofitClient;
    private String API_KEY;
    public static final String sort = "r";
    private FoodDatabase foodDatabase;
    private AppExecutor appExecutor;
    public static final int PAGE_LIMIT = 1500;
    //todo

    @Inject
    public RecipeRepository(FoodDatabase database,RetrofitClient client,String API_KEY,AppExecutor appExecutor) {
        this.foodDatabase = database;
        this.retrofitClient = client;
        this.API_KEY = API_KEY;
        this.appExecutor = appExecutor;
    }

    public void getNextRecipe(){
        int nextPage = getRamdomPageNum();
        retrofitClient.getFoodService().searchRecipes(API_KEY,sort,nextPage)
                .enqueue(new Callback<RecipeSearchResponse>() {
                    @Override
                    public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                        //todo: handle(error:limit)
                        if(response.isSuccessful()){
                            RecipeSearchResponse searchResponse = response.body();
                            if(searchResponse.getCount()==0){
                                //getNextRecipe();
                                Log.d(TAG,"the returned recipes are null");
                            }else{
                                Recipe recipe = searchResponse.getFirstRecipe();
                                if (recipe!=null){
                                    RecipeEvent recipeEvent = new RecipeEvent(recipe,null,null);
                                    EventBus.getDefault().post(recipeEvent);
                                }else{
                                    RecipeEvent recipeEvent = new RecipeEvent(null,null,response.errorBody().toString());
                                    EventBus.getDefault().post(recipeEvent);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                        RecipeEvent recipeEvent = new RecipeEvent(null,null,t.getMessage());
                        EventBus.getDefault().post(recipeEvent);
                    }
                });

    }

    private int getRamdomPageNum(){
        return new Random().nextInt(PAGE_LIMIT)+1;
    }

    public void saveRecipe(final Recipe recipe){
        appExecutor.getThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                foodDatabase.getRecipeDao().insertRecipe(recipe);
            }
        });

    }
}
