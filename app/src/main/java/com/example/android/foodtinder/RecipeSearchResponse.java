package com.example.android.foodtinder;

import java.util.List;

class RecipeSearchResponse {
    private int count;
    private int page;
    private List<Recipe> recipes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Recipe getFirstRecipe(){
        Recipe first = null;
        if(!recipes.isEmpty()){
            first = recipes.get(0);
        }
        return first;
    }
}
