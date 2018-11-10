package com.example.android.foodtinder.mvp;

import com.example.android.foodtinder.db.Recipe;

import javax.inject.Inject;

public class RecipeInteractorImpl implements RecipeInteractor {
    @Inject
    RecipeRepository recipeRepository;


    public RecipeInteractorImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void getNextRecipe() {
        recipeRepository.getNextRecipe();
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipeRepository.saveRecipe(recipe);
    }

}
