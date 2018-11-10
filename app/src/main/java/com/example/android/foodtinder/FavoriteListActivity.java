package com.example.android.foodtinder;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.android.foodtinder.app.FoodTinderApplication;
import com.example.android.foodtinder.db.Recipe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

;

public class FavoriteListActivity extends AppCompatActivity {
    @BindView(R.id.custom_toolbar)
    Toolbar customToolbar;
    @BindView(R.id.favorite_list_rv)
    RecyclerView favoriteListRv;

    private List<Recipe> favoriteRecipesList;
    private FavoriteListAdapter favoriteListAdapter;
    @Inject
    public FavoriteRecipeRepository favoriteRecipeRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        ButterKnife.bind(this);
        setSupportActionBar(customToolbar);
        setUpInjection();
        setUpRecyclerView();
        setUpAdapter();
        getAllFavoriteRecipes();
    }

    private void setUpInjection() {
        ((FoodTinderApplication)getApplicationContext()).getFavoriteRecipesComponent()
                .inject(this);
    }

    private void getAllFavoriteRecipes() {
        FavoriteRecipeViewModel favoriteRecipeViewModel = ViewModelProviders.of(this,new MyViewModelFactory(favoriteRecipeRepository))
                .get(FavoriteRecipeViewModel.class);
        favoriteRecipeViewModel.getFavoriteRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                favoriteListAdapter.newRecipes(recipes);
            }
        });
    }

    private void setUpAdapter() {
        favoriteRecipesList = new ArrayList<>();
        favoriteListAdapter = new FavoriteListAdapter(favoriteRecipesList, Glide.with(this));
        favoriteListRv.setAdapter(favoriteListAdapter);
    }

    private void setUpRecyclerView() {
        favoriteListRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }
}
