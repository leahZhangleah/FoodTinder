package com.example.android.foodtinder.app;

import android.app.Application;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.example.android.foodtinder.di.ContextModule;
import com.example.android.foodtinder.di.DaggerFoodRecipeComponent;
import com.example.android.foodtinder.di.FoodRecipeComponent;
import com.example.android.foodtinder.di.FoodRecipeModule;
import com.example.android.foodtinder.mvp.RecipeView;

public class FoodTinderApplication extends Application {
    private FoodRecipeComponent foodRecipeComponent;
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public FoodRecipeComponent getFoodRecipeComponent(RecipeView recipeView, ImageView recipeImg, RequestListener requestListener) {
        return DaggerFoodRecipeComponent.builder()
                .foodRecipeModule(new FoodRecipeModule(recipeView,recipeImg,requestListener))
                .contextModule(new ContextModule(this))
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
