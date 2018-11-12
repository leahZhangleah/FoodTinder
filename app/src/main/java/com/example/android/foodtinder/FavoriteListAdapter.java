package com.example.android.foodtinder;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.android.foodtinder.db.Recipe;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder> {
    private List<Recipe> favoriteRecipesList;
    private RequestManager requestManager;

    public FavoriteListAdapter(List<Recipe> favoriteRecipesList, RequestManager requestManager) {
        this.favoriteRecipesList = favoriteRecipesList;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_recipe,parent,false);
        return new FavoriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Recipe recipe = favoriteRecipesList.get(position);
        if(recipe!=null){
            holder.favoriteRecipeTitle.setText(recipe.getTitle());
            holder.favoriteRecipePb.setVisibility(View.VISIBLE);
           requestManager.load(recipe.getImage_url())
                   .listener(new MyRequestListener(holder))
                   .into(holder.favoriteRecipeImg);
        }
    }

    @Override
    public void onViewRecycled(@NonNull FavoriteViewHolder holder) {
        super.onViewRecycled(holder);
        requestManager.clear(holder.itemView);
    }

    @Override
    public int getItemCount() {
        if(!favoriteRecipesList.isEmpty()) return favoriteRecipesList.size();
        return 0;
    }

    public void newRecipes(List<Recipe> recipes){
        if(recipes!=favoriteRecipesList&&recipes!=null){
            favoriteRecipesList = recipes;
            notifyDataSetChanged();
        }

    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.favorite_recipe_img)
        ImageView favoriteRecipeImg;
        @BindView(R.id.favorite_recipe_title)
        TextView favoriteRecipeTitle;
        @BindView(R.id.share_btn)
        ShareButton shareButton;
        @BindView(R.id.send_btn)
        SendButton sendButton;
        @BindView(R.id.favorite_recipe_pb)
        ProgressBar favoriteRecipePb;
        @BindView(R.id.error_msg)
        TextView errorMsg;
        public FavoriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private class MyRequestListener implements RequestListener {
        FavoriteViewHolder viewHolder;

        public MyRequestListener(FavoriteViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
            viewHolder.errorMsg.setText(e.getMessage());
            return true;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
            viewHolder.favoriteRecipePb.setVisibility(View.INVISIBLE);
            if(resource!=null){
                if(resource instanceof Drawable){
                    viewHolder.favoriteRecipeImg.setImageDrawable((Drawable)resource);
                    //todo: handle other format situations
                }
            }else{
                viewHolder.favoriteRecipeImg.setImageResource(R.drawable.no_photo);
            }
            return true;
        }
    }

    public void removeAt(int position){
        favoriteRecipesList.remove(position);
        notifyItemRemoved(position);
    }
}
