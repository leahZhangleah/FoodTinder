package com.example.android.foodtinder.mvp;

import com.example.android.foodtinder.db.Recipe;

public interface RecipeInteractor {
    void getNextRecipe();
    void saveRecipe(Recipe recipe);
}
