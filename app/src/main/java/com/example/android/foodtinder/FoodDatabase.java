package com.example.android.foodtinder;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = Recipe.class,version = 1)
public abstract class FoodDatabase extends RoomDatabase {

    public abstract RecipeDao getRecipeDao();
}
