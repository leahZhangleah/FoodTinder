package com.example.android.foodtinder;

import android.arch.lifecycle.LiveData;

import com.example.android.foodtinder.db.FoodDatabase;
import com.example.android.foodtinder.db.Recipe;

import java.util.List;

public class FavoriteRecipeRepository {

    FoodDatabase foodDatabase;

    public FavoriteRecipeRepository(FoodDatabase foodDatabase) {
        this.foodDatabase = foodDatabase;
    }

    public LiveData<List<Recipe>> getFavoriteRecipes() {

        return foodDatabase.getRecipeDao().getRecipes();
    }
}
