package com.example.android.foodtinder;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.android.foodtinder.db.Recipe;
import com.facebook.login.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodRecipesActivity extends AppCompatActivity implements RecipeView,RequestListener {
    private static final String TAG = "FoodRecipesActivity";
    @BindView(R.id.recipeImg)
    ImageView recipeImg;
    @BindView(R.id.dismissBtn)
    ImageButton dismissBtn;
    @BindView(R.id.saveBtn)
    ImageButton saveBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recipe_layout)
    ConstraintLayout recipeLayout;

    private Recipe currentRecipe;
    private RecipePresenter recipePresenter;
    private GlideImageLoader glideImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recipes);
        ButterKnife.bind(this);
        recipePresenter = new RecipePresenterImpl(this,getString(R.string.FOOD_API_KEY));
        recipePresenter.onCreate();
        recipePresenter.getNextRecipe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recipePresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.dismissBtn)
    public void dismissRecipe(){
        recipePresenter.dismissRecipe();
    }

    @OnClick(R.id.saveBtn)
    public void saveRecipe(){
        if(currentRecipe!=null){
            recipePresenter.saveRecipe(currentRecipe);
        }
    }


    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDismissAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.dismiss_animation);
        recipeImg.startAnimation(animation);
    }

    @Override
    public void setSaveAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.save_animation);
        recipeImg.startAnimation(animation);
    }

    @Override
    public void setImage(Drawable image) {
        if(image!=null){
            recipeImg.setImageDrawable(image);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOutBtn:
                //TODO:can implement this in application and call method here
                LoginManager.getInstance().logOut();
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onReceiveNextRecipe(Recipe recipe) {
        if(recipe!=null) currentRecipe = recipe;
        glideImageLoader = new GlideImageLoader(recipeImg,this,this);
        glideImageLoader.loadImage(recipe);
    }

    @Override
    public void onReceiveResponseMsg(String responseMsg) {
        Snackbar.make(recipeLayout,responseMsg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveError(String errorMsg) {
        Snackbar.make(recipeLayout,errorMsg,Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
        if(e!=null){
            recipePresenter.onImageLoadFailed(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
        if(resource instanceof Drawable){
            recipePresenter.onImageResourceReady((Drawable)resource);
        }
        return true;
    }
}
