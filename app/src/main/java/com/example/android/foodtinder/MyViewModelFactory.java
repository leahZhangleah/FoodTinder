package com.example.android.foodtinder;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    FavoriteRecipeRepository favoriteRecipeRepository;

    public MyViewModelFactory(FavoriteRecipeRepository favoriteRecipeRepository) {
        this.favoriteRecipeRepository = favoriteRecipeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new FavoriteRecipeViewModel(favoriteRecipeRepository);
    }
}
