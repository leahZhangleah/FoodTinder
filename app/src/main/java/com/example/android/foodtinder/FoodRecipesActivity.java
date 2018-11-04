package com.example.android.foodtinder;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.android.foodtinder.db.Recipe;
import com.facebook.login.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodRecipesActivity extends AppCompatActivity implements RecipeView {
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

    //private Recipe currentRecipe;
    private RecipePresenter recipePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recipes);
        ButterKnife.bind(this);
        recipePresenter = new RecipePresenterImpl(this,getString(R.string.FOOD_API_KEY));
        recipePresenter.onCreate();
        getNextRecipe();
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
        recipeImg.setImageResource(0);
        recipeImg.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getNextRecipe();
    }

    @OnClick(R.id.saveBtn)
    public void saveRecipe(){
        recipeImg.setImageResource(0);
        recipeImg.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getNextRecipe();
    }

    private void getNextRecipe(){
        recipePresenter.getNextRecipe();
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
            Log.d(TAG,"id: "+recipe.getRecipe_id()+"title: "+recipe.getTitle()+
                    "imageurl: "+recipe.getImage_url()+"sourceurl: "+recipe.getSource_url()
                    +"favorite: "+recipe.getFavorite());
            recipeImg.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Glide.with(this).load(recipe.getImage_url()).into(recipeImg);


        }
    }

    @Override
    public void onReceiveResponseMsg(String responseMsg) {
        recipeImg.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Snackbar.make(recipeLayout,responseMsg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveError(String errorMsg) {
        recipeImg.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        Snackbar.make(recipeLayout,errorMsg,Snackbar.LENGTH_SHORT).show();

    }
}
