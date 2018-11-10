package com.example.android.foodtinder.di;

import com.example.android.foodtinder.FoodRecipesActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FoodRecipeModule.class,ContextModule.class})
public interface FoodRecipeComponent {
    void inject(FoodRecipesActivity activity);

}
