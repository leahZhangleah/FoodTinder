package com.example.android.foodtinder;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.foodtinder.db.Recipe;

import java.util.List;

public class FavoriteRecipeViewModel extends ViewModel {
    FavoriteRecipeRepository favoriteRecipeRepository;

    public FavoriteRecipeViewModel(FavoriteRecipeRepository favoriteRecipeRepository) {
        this.favoriteRecipeRepository = favoriteRecipeRepository;
    }

    public LiveData<List<Recipe>> getFavoriteRecipes(){
        return favoriteRecipeRepository.getFavoriteRecipes();
    }
}
