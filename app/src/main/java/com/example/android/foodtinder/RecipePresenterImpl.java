package com.example.android.foodtinder;

import android.util.Log;

import com.example.android.foodtinder.db.Recipe;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class RecipePresenterImpl implements RecipePresenter {
    private static final String TAG = "RecipePresenterImpl";
    RecipeView recipeView;
    RecipeInteractor recipeInteractor;

    public RecipePresenterImpl(RecipeView recipeView,String ApiKey) {
        this.recipeView = recipeView;
        recipeInteractor = new RecipeInteractorImpl(ApiKey);
    }

    @Override
    public void getNextRecipe() {
        recipeInteractor.getNextRecipe();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe()
    public void onReceiveEvent(RecipeEvent recipeEvent){
        if(recipeEvent.getCurrentRecipe()!=null){
            Recipe recipe = recipeEvent.getCurrentRecipe();
            recipeView.onReceiveNextRecipe(recipeEvent.currentRecipe);
        }else{
            if(recipeEvent.getResponseMsg()!=null){
                recipeView.onReceiveResponseMsg(recipeEvent.getResponseMsg());
            }else{
                recipeView.onReceiveError(recipeEvent.getRequestError());
            }
        }
    }
}
