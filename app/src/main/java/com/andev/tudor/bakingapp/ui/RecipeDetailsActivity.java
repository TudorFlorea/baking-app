package com.andev.tudor.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.adapters.RecipeStepAdapter;
import com.andev.tudor.bakingapp.data.Recipe;
import com.andev.tudor.bakingapp.data.Step;
import com.andev.tudor.bakingapp.ui.fragments.RecipeDetailsFragment;
import com.andev.tudor.bakingapp.ui.fragments.RecipeStepDetailsFragment;
import com.andev.tudor.bakingapp.utils.Constants;
import com.andev.tudor.bakingapp.utils.InterfaceUtils;
import com.andev.tudor.bakingapp.utils.SharedPreferencesUtils;
import com.andev.tudor.bakingapp.widget.IngredientsWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepSelected{

    private RecipeStepAdapter mRecipeStepAdapter;
    private final String RECIPE_EXTRA = "recipe_extra";
    private final String STEP_TAG = "step";
    private final String STEP_DETAILS_FRAGMENT_TAG = "step_details_fragment";
    private boolean mTwoPane;
    private FragmentManager mFragmentManager;
    //private RecipeStepDetailsFragment mStepDetailsFragment;

    @BindView(R.id.recipe_details_fl) FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        if (findViewById(R.id.recipe_details_two_pane) != null) {
            mTwoPane = true;
        }

        Intent i = getIntent();

        if (i.hasExtra(Constants.CURRENT_RECIPE_INDEX_TAG)) {
            int recipeIndex = i.getIntExtra(Constants.CURRENT_RECIPE_INDEX_TAG, 0);
            boolean isSharedPrefIndexUpdated = SharedPreferencesUtils.setIntToSharedPrefsForKey(Constants.SHARED_PREF_CURRENT_RECIPE_KEY, recipeIndex, getApplicationContext());

            Recipe recipe = i.getExtras().getParcelable(RECIPE_EXTRA);
            boolean isRecipeNameUpdated = SharedPreferencesUtils.setStringToSharedPrefsForKey(Constants.SHARED_PREF_CURRENT_RECIPE_NAME_KEY, recipe.getName(), getApplicationContext());

            if (isSharedPrefIndexUpdated) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        IngredientsWidgetProvider.sendRefreshBroadcast(getApplicationContext());
                    }
                });
            }

        }


        if (i.hasExtra(RECIPE_EXTRA)) {
            try {

                mFragmentManager = getSupportFragmentManager();

                RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
                detailsFragment.setArguments(i.getExtras());
                mFragmentManager.beginTransaction()
                        .replace(R.id.recipe_details_fl, detailsFragment).commit();

                //mStepDetailsFragment = (RecipeStepDetailsFragment) mFragmentManager.findFragmentByTag(STEP_DETAILS_FRAGMENT_TAG);

            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }

        }

    }

    @Override
    public void stepSelected(Bundle stepsData) {
        if (mTwoPane) {
                RecipeStepDetailsFragment mStepDetailsFragment;
                mStepDetailsFragment = new RecipeStepDetailsFragment();
                mStepDetailsFragment.setArguments(stepsData);
                mFragmentManager.beginTransaction()
                        .replace(R.id.step_details_container, mStepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG)
                        .commit();
        } else {
            Intent i = new Intent(this, StepDetailsActivity.class);
            i.putExtra(Constants.STEPS_DATA_TAG, stepsData);
            startActivity(i);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
