package com.andev.tudor.bakingapp.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.adapters.RecipeCardAdapter;
import com.andev.tudor.bakingapp.data.Recipe;
import com.andev.tudor.bakingapp.data.RecipeFactory;
import com.andev.tudor.bakingapp.utils.DisplayUtils;
import com.andev.tudor.bakingapp.utils.InternetUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>{

    private final int RECIPE_CARD_WIDTH = 300;

    private RecipeCardAdapter mRecipeCardAdapter;

    @BindView(R.id.recipe_cards_rv) RecyclerView mRecipeCardsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mRecipeCardsRV.setLayoutManager(
                new GridLayoutManager(MainActivity.this,
                        DisplayUtils.numberOfColumns(getWindowManager(), RECIPE_CARD_WIDTH ),
                        GridLayoutManager.VERTICAL,
                        false)
        );


        mRecipeCardAdapter = new RecipeCardAdapter(this, null);
        mRecipeCardsRV.setAdapter(mRecipeCardAdapter);

        if (InternetUtils.isNetworkAvailable(this)) {
            getSupportLoaderManager().restartLoader(100, null, this);
        }



    }


    @NonNull
    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Nullable
            @Override
            public ArrayList<Recipe> loadInBackground() {
                return RecipeFactory.getAllRecipes();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes) {

        if(recipes != null) {
            mRecipeCardAdapter =  new RecipeCardAdapter(MainActivity.this, recipes);
            mRecipeCardsRV.setAdapter(mRecipeCardAdapter);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Recipe>> loader) {

    }
}
