package com.example.android.foodtinder.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Recipe.class,version = 1,exportSchema = false)
public abstract class FoodDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "food database";
    private static volatile FoodDatabase foodDatabase;
    public static FoodDatabase getFoodDatabase(final Context context){
        if(foodDatabase==null){
            synchronized (FoodDatabase.class){
                foodDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        FoodDatabase.class,DATABASE_NAME)
                        .build();
            }
        }
        return foodDatabase;
    }
    public abstract RecipeDao getRecipeDao();


}
