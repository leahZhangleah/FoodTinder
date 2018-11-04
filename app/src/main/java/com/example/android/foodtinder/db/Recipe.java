package com.example.android.foodtinder.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey
    @NonNull
    //@ColumnInfo(name = "recipe_id")
    private String recipe_id;
    private String title;
    //@ColumnInfo(name = "image_url")
    private String image_url;
    //@ColumnInfo(name = "source_url")
    private String source_url;
    //@ColumnInfo(name ="is_favorite")
    private boolean favorite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return recipe_id == recipe.recipe_id && favorite == recipe.favorite &&
                title == recipe.title && image_url == recipe.image_url &&
                source_url == recipe.source_url;
    }

    /*
    @Override
    public int hashCode() {

        return Objects.hash(id, title, image_url, source_url, is_favorite);
    }*/

    @NonNull
    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(@NonNull String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
