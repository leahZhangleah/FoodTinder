package com.example.android.foodtinder;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.example.android.foodtinder.db.Recipe;

public class GlideImageLoader {
    ImageView recipeImg;
    Context context;
    RequestListener requestListener;

    public GlideImageLoader(ImageView recipeImg, Context context, RequestListener requestListener) {
        this.recipeImg = recipeImg;
        this.context = context;
        this.requestListener = requestListener;
    }

    public void loadImage(Recipe recipe){
        if(recipe!=null){
            Glide.with(context)
                    .load(recipe.getImage_url())
                    .listener(requestListener)
                    .into(recipeImg);
        }
    }

}
