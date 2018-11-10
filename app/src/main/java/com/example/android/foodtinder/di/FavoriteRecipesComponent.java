package com.example.android.foodtinder.di;

import com.example.android.foodtinder.FavoriteListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FavoriteRecipesModule.class,ContextModule.class})
public interface FavoriteRecipesComponent {
    void inject(FavoriteListActivity activity);
}
