package com.andev.tudor.bakingapp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.ui.IngredientsActivity;
import com.andev.tudor.bakingapp.ui.RecipeDetailsActivity;
import com.andev.tudor.bakingapp.ui.StepDetailsActivity;
import com.andev.tudor.bakingapp.adapters.RecipeStepAdapter;
import com.andev.tudor.bakingapp.data.Recipe;
import com.andev.tudor.bakingapp.data.Step;
import com.andev.tudor.bakingapp.utils.Constants;
import com.andev.tudor.bakingapp.utils.InterfaceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment implements InterfaceUtils.RecipeStepListener {

    private RecipeStepAdapter mRecipeStepAdapter;
    private final String RECIPE_EXTRA = "recipe_extra";
    private final String STEP_EXTRA = "step_extra";
    private OnStepSelected mStepListener;

    @BindView(R.id.recipe_steps_rv) RecyclerView mRecipeStepsRV;
    @BindView(R.id.recipe_ingredients_cv) CardView mIngredientsCV;

    public RecipeDetailsFragment() {

    }

    public interface OnStepSelected {
        public void stepSelected(Bundle stepData);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mStepListener = (OnStepSelected) context;
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            final Recipe recipe = getArguments().getParcelable(RECIPE_EXTRA);
            mRecipeStepsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
            mRecipeStepAdapter = new RecipeStepAdapter(getActivity(), recipe.getSteps(), this);
            mRecipeStepsRV.setAdapter(mRecipeStepAdapter);

            mIngredientsCV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), IngredientsActivity.class);
                    i.putExtra(Constants.RECIPE_EXTRA, recipe);
                    startActivity(i);
                }
            });

        }
        catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    @Override
    public void onStepClick(Bundle stepsData) {
            mStepListener.stepSelected(stepsData);
    }
}
