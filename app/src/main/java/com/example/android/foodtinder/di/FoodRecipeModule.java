package com.example.android.foodtinder.di;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.example.android.foodtinder.AppExecutor;
import com.example.android.foodtinder.GlideImageLoader;
import com.example.android.foodtinder.R;
import com.example.android.foodtinder.db.FoodDatabase;
import com.example.android.foodtinder.mvp.RecipeInteractor;
import com.example.android.foodtinder.mvp.RecipeInteractorImpl;
import com.example.android.foodtinder.mvp.RecipePresenter;
import com.example.android.foodtinder.mvp.RecipePresenterImpl;
import com.example.android.foodtinder.mvp.RecipeRepository;
import com.example.android.foodtinder.mvp.RecipeView;
import com.example.android.foodtinder.retrofit.RetrofitClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FoodRecipeModule {
    RecipeView recipeView;
    ImageView recipeImg;
    RequestListener requestListener;

    public FoodRecipeModule(RecipeView recipeView, ImageView recipeImg, RequestListener requestListener) {
        this.recipeView = recipeView;
        this.recipeImg = recipeImg;
        this.requestListener = requestListener;
    }

    @Provides
    @Singleton
    public RecipePresenter provideRecipePresenter(RecipeInteractor recipeInteractor){
        return new RecipePresenterImpl(recipeView,recipeInteractor);
    }

    @Provides
    @Singleton
    public RecipeInteractor provideRecipeInteractor(RecipeRepository recipeRepository){
        return new RecipeInteractorImpl(recipeRepository);
    }

    @Provides
    @Singleton
    public RecipeRepository provideRecipeRepository(FoodDatabase foodDatabase, RetrofitClient retrofitClient, String apiKey, AppExecutor appExecutor){
        return new RecipeRepository(foodDatabase,retrofitClient,apiKey,appExecutor);
    }

    @Inject
    @Provides
    @Singleton
    public FoodDatabase provideFoodDatabase(Context context){
        return FoodDatabase.getFoodDatabase(context);
    }

    @Provides
    @Singleton
    public RetrofitClient provideRetrofitClient(){
        return new RetrofitClient();
    }

    @Inject
    @Provides
    @Singleton
    public String provideApiKey(Context context){
        return context.getString(R.string.FOOD_API_KEY);
    }

    @Inject
    @Provides
    @Singleton
    public GlideImageLoader provideGlideImageLoader(Context context){
        return new GlideImageLoader(recipeImg,context,requestListener);
    }

    @Provides
    @Singleton
    public AppExecutor provideAppExecutor(){
        return AppExecutor.getInstance();
    }




}
