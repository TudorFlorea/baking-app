package com.andev.tudor.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.adapters.RecipeIngredientsAdapter;
import com.andev.tudor.bakingapp.data.Ingredient;
import com.andev.tudor.bakingapp.data.Recipe;
import com.andev.tudor.bakingapp.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsActivity extends AppCompatActivity {

    @BindView(R.id.ingredients_rv) RecyclerView mIngredientsRV;
    private RecipeIngredientsAdapter mIngredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        ButterKnife.bind(this);

        Intent i = getIntent();

        if (i.hasExtra(Constants.RECIPE_EXTRA)) {

            try {
                Recipe recipe = i.getExtras().getParcelable(Constants.RECIPE_EXTRA);
                ArrayList<Ingredient> ingredients = recipe.getIngredients();
                mIngredientsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
                mIngredientsAdapter = new RecipeIngredientsAdapter(this, ingredients);
                mIngredientsRV.setAdapter(mIngredientsAdapter);

            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
