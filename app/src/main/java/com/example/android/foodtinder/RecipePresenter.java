package com.example.android.foodtinder;

import android.graphics.drawable.Drawable;

import com.example.android.foodtinder.db.Recipe;

public interface RecipePresenter {
    void getNextRecipe();
    void onCreate();
    void onDestroy();
    void dismissRecipe();
    void saveRecipe(Recipe recipe);
    void onImageLoadFailed(String error);
    void onImageResourceReady(Drawable image);
}
