package com.example.android.foodtinder.eventbus;

import android.support.annotation.Nullable;

import com.example.android.foodtinder.db.Recipe;

public class RecipeEvent {
    Recipe currentRecipe;
    String responseMsg;
    String requestError;

    public RecipeEvent(@Nullable Recipe currentRecipe, @Nullable  String responseMsg,  @Nullable String requestError) {
        this.currentRecipe = currentRecipe;
        this.responseMsg = responseMsg;
        this.requestError = requestError;
    }

    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public String getRequestError() {
        return requestError;
    }
}
