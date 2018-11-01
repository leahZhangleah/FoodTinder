package com.example.android.foodtinder;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes")
    List<Recipe> getRecipes();
}
