package com.example.android.foodtinder;

import android.arch.lifecycle.LiveData;

import com.example.android.foodtinder.db.FoodDatabase;
import com.example.android.foodtinder.db.Recipe;

import java.util.List;

public class FavoriteRecipeRepository {

    private FoodDatabase foodDatabase;
    private AppExecutor appExecutor;

    public FavoriteRecipeRepository(FoodDatabase foodDatabase, AppExecutor appExecutor) {
        this.foodDatabase = foodDatabase;
        this.appExecutor = appExecutor;
    }

    public LiveData<List<Recipe>> getFavoriteRecipes() {

        return foodDatabase.getRecipeDao().getRecipes();
    }

    public void removeRecipe(final Recipe recipe) {
        appExecutor.getSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                foodDatabase.getRecipeDao().deleteRecipe(recipe);
            }
        });

    }
}
