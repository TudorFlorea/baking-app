package com.andev.tudor.bakingapp.ui;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.data.Step;
import com.andev.tudor.bakingapp.ui.fragments.RecipeStepDetailsFragment;
import com.andev.tudor.bakingapp.utils.Constants;
import com.andev.tudor.bakingapp.utils.SharedPreferencesUtils;
import com.andev.tudor.bakingapp.widget.IngredientsWidgetProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailsActivity extends AppCompatActivity {

    @BindView(R.id.previous_step_btn) Button mPreviousStepBtn;
    @BindView(R.id.next_step_btn) Button mNextStepBtn;

    private final String STEP_DETAILS_FRAGMENT_TAG = "step_details_fragment";
    private final String RECIPE_EXTRA = "recipe_extra";
    private Bundle mStepsData;
    private ArrayList<Step> mSteps;
    private int mCurrentStepIndex;
    private FragmentManager mFragmentManager;

    private RecipeStepDetailsFragment mStepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        ButterKnife.bind(this);

        Intent i = getIntent();

        mStepsData = i.getExtras().getBundle(Constants.STEPS_DATA_TAG);
        mSteps = mStepsData.getParcelableArrayList(Constants.STEPS_ARRAY_LIST_TAG);
        mCurrentStepIndex = mStepsData.getInt(Constants.CURRENT_STEP_INDEX_TAG);



        mFragmentManager = getSupportFragmentManager();
        mStepDetailsFragment = (RecipeStepDetailsFragment) mFragmentManager.findFragmentByTag(STEP_DETAILS_FRAGMENT_TAG);

        if (mCurrentStepIndex == 0) {
            mPreviousStepBtn.setVisibility(View.INVISIBLE);
        }

        if (mCurrentStepIndex == 0) {
            mPreviousStepBtn.setVisibility(View.INVISIBLE);
        }

        mPreviousStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentStepIndex--;
                if (mCurrentStepIndex < (mSteps.size() - 1)) mNextStepBtn.setVisibility(View.VISIBLE);
                if (mCurrentStepIndex == 0) mPreviousStepBtn.setVisibility(View.INVISIBLE);

                Bundle stepsData = new Bundle();
                stepsData.putParcelableArrayList(Constants.STEPS_ARRAY_LIST_TAG, mSteps);
                stepsData.putInt(Constants.CURRENT_STEP_INDEX_TAG, mCurrentStepIndex);
                replaceStepFragment(stepsData);
            }
        });

        mNextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentStepIndex++;
                if (mCurrentStepIndex > 0) mPreviousStepBtn.setVisibility(View.VISIBLE);
                if (mCurrentStepIndex == (mSteps.size() - 1)) mNextStepBtn.setVisibility(View.INVISIBLE);

                    Bundle stepsData = new Bundle();
                    stepsData.putParcelableArrayList(Constants.STEPS_ARRAY_LIST_TAG, mSteps);
                    stepsData.putInt(Constants.CURRENT_STEP_INDEX_TAG, mCurrentStepIndex);
                    replaceStepFragment(stepsData);

            }
        });

        if (i.hasExtra(Constants.STEPS_DATA_TAG)) {
            if (mStepDetailsFragment == null) {
                mStepDetailsFragment = new RecipeStepDetailsFragment();

                mStepDetailsFragment.setArguments(mStepsData);
                mFragmentManager.beginTransaction()
                        .replace(R.id.step_details_container, mStepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG)
                        .commit();
            }
        }


    }

    private void replaceStepFragment(Bundle stepData) {
        mStepDetailsFragment = new RecipeStepDetailsFragment();
        mStepDetailsFragment.setArguments(stepData);
        mFragmentManager.beginTransaction()
                .replace(R.id.step_details_container, mStepDetailsFragment, STEP_DETAILS_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
