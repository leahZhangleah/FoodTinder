package com.example.android.foodtinder;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey
    private int id;
    private String title;
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "source_url")
    private String sourceUrl;
    @ColumnInfo(name ="is_favorite")
    private boolean favorite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && favorite == recipe.favorite &&
                title == recipe.title && imageUrl == recipe.imageUrl &&
                sourceUrl == recipe.sourceUrl;
    }

    /*
    @Override
    public int hashCode() {

        return Objects.hash(id, title, image_url, source_url, is_favorite);
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
