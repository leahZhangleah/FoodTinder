package com.example.android.foodtinder;

import android.graphics.drawable.Drawable;

import com.example.android.foodtinder.db.Recipe;

public interface RecipeView {
    void onReceiveNextRecipe(Recipe recipe);
    void onReceiveResponseMsg(String responseMsg);
    void onReceiveError(String errorMsg);
    void hideProgress();
    void showProgress();
    void setDismissAnimation();
    void setSaveAnimation();
    void setImage(Drawable image);
}
