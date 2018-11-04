package com.example.android.foodtinder;

import com.example.android.foodtinder.db.Recipe;

public interface RecipeView {
    void onReceiveNextRecipe(Recipe recipe);
    void onReceiveResponseMsg(String responseMsg);
    void onReceiveError(String errorMsg);
}
