package com.example.android.foodtinder.retrofit;

import android.util.Log;

import com.example.android.foodtinder.db.Recipe;

import java.util.List;

public class RecipeSearchResponse {
    private static final String TAG = "RecipeSearchResponse";
    private int count;
    private List<Recipe> recipes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Recipe getFirstRecipe(){
        Recipe recipe = null;
        if(!recipes.isEmpty()){
            recipe = recipes.get(0);
        }
        Log.d(TAG,"id: "+recipe.getRecipe_id()+"title: "+recipe.getTitle()+
                "imageurl: "+recipe.getImage_url()+"sourceurl: "+recipe.getSource_url()
                +"favorite: "+recipe.getFavorite());
        return recipe;
    }
}
