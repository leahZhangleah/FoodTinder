package com.example.android.foodtinder;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.example.android.foodtinder.app.FoodTinderApplication;
import com.example.android.foodtinder.db.Recipe;
import com.example.android.foodtinder.mvp.RecipePresenter;
import com.example.android.foodtinder.mvp.RecipeView;
import com.facebook.login.LoginManager;

import javax.inject.Inject;

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
    @Inject
    public RecipePresenter recipePresenter;
    @Inject
    public GlideImageLoader glideImageLoader;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recipes);
        ButterKnife.bind(this);
        ((FoodTinderApplication)getApplicationContext()).getFoodRecipeComponent(this,recipeImg,this)
                .inject(this);
        recipePresenter.onCreate();
        recipePresenter.getNextRecipe();
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        recipeImg.setOnTouchListener(new MyTouchListener());
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
        animation.setAnimationListener(new MyAnimationListener());
        recipeImg.startAnimation(animation);
        //recipeImg.setImageResource(0);
    }

    @Override
    public void setSaveAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.save_animation);
        animation.setAnimationListener(new MyAnimationListener());
        recipeImg.startAnimation(animation);
        //recipeImg.setImageResource(0);
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
        if(recipe!=null){
            currentRecipe = recipe;
            glideImageLoader.loadImage(recipe);
        }

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

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String TAG = "MyGestureListener";
        public static final int SWIPE_THRESHOLD = 100;
        public static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();
            /*
             if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) >     SWIPE_THRESHOLD_VELOCITY) {
       // Right to left, your code here
       return true;
    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&     Math.abs(velocityX) >  SWIPE_THRESHOLD_VELOCITY) {
       // Left to right, your code here
       return true;
    }
    if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) >     SWIPE_THRESHOLD_VELOCITY) {
       // Bottom to top, your code here
       return true;
    } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&    Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
       // Top to bottom, your code here
       return true;
    }
             */
            if(Math.abs(diffX)>Math.abs(diffY)&& Math.abs(diffX) > SWIPE_THRESHOLD
                    && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                if(diffX > 0){
                    saveRecipe();
                }else{
                    dismissRecipe();
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return mDetector.onTouchEvent(event);
        }
    }

    private class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            recipeImg.setImageResource(0);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
