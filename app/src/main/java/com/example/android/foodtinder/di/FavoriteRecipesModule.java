package com.example.android.foodtinder.di;

import android.content.Context;

import com.example.android.foodtinder.AppExecutor;
import com.example.android.foodtinder.FavoriteRecipeRepository;
import com.example.android.foodtinder.db.FoodDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoriteRecipesModule {

    @Inject
    @Provides
    @Singleton
    public FoodDatabase provideFoodDatabase(Context context){
        return FoodDatabase.getFoodDatabase(context);
    }

    @Provides
    @Singleton
    public FavoriteRecipeRepository provideFavoriteRecipeRepository(FoodDatabase foodDatabase, AppExecutor appExecutor){
        return new FavoriteRecipeRepository(foodDatabase,appExecutor);
    }

    @Provides
    @Singleton
    public AppExecutor provideAppExecutor(){
        return AppExecutor.getInstance();
    }

}
