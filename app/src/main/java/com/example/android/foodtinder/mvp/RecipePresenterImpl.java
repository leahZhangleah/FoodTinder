package com.example.android.foodtinder.mvp;

import android.graphics.drawable.Drawable;

import com.example.android.foodtinder.db.Recipe;
import com.example.android.foodtinder.eventbus.RecipeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class RecipePresenterImpl implements RecipePresenter {
    private static final String TAG = "RecipePresenterImpl";
    RecipeView recipeView;
    RecipeInteractor recipeInteractor;

    public RecipePresenterImpl(RecipeView recipeView,RecipeInteractor recipeInteractor) {
        this.recipeView = recipeView;
        this.recipeInteractor = recipeInteractor;
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

    @Override
    public void dismissRecipe() {
        recipeView.showProgress();
        recipeView.setDismissAnimation();
        getNextRecipe();
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipeView.showProgress();
        recipeView.setSaveAnimation();
        recipeInteractor.saveRecipe(recipe);
        getNextRecipe();
        //todo: save recipe to db
    }

    @Override
    public void onImageLoadFailed(String error) {
        recipeView.hideProgress();
        recipeView.onReceiveError(error);
    }

    @Override
    public void onImageResourceReady(Drawable image) {
        recipeView.hideProgress();
        recipeView.setImage(image);
    }

    @Subscribe()
    public void onReceiveEvent(RecipeEvent recipeEvent){
        if(recipeEvent.getCurrentRecipe()!=null){
            Recipe recipe = recipeEvent.getCurrentRecipe();
            recipeView.onReceiveNextRecipe(recipe);
        }else{
            if(recipeEvent.getResponseMsg()!=null){
                recipeView.hideProgress();
                recipeView.onReceiveResponseMsg(recipeEvent.getResponseMsg());
            }else{
                recipeView.onReceiveError(recipeEvent.getRequestError());
            }
        }
    }
}
