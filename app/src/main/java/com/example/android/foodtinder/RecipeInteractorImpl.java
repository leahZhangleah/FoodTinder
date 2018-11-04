package com.example.android.foodtinder;

import java.util.Random;

public class RecipeInteractorImpl implements RecipeInteractor {
    //todo
    RecipeRepository recipeRepository;
    public static final int PAGE_LIMIT = 1000;

    public RecipeInteractorImpl(String API_KEY) {
        this.recipeRepository = new RecipeRepository(API_KEY);
    }

    @Override
    public void getNextRecipe() {
        int nextPage = getRamdomPageNum();
        recipeRepository.getNextRecipe(nextPage);
    }

    private int getRamdomPageNum(){
        return new Random().nextInt(PAGE_LIMIT)+1;
    }
}
