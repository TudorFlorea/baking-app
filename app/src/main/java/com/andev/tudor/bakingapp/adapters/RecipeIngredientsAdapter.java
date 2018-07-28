package com.andev.tudor.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andev.tudor.bakingapp.R;
import com.andev.tudor.bakingapp.data.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientViewHolder> {

    private Context mContext;
    private ArrayList<Ingredient> mIngredients;

    public RecipeIngredientsAdapter(Context context, ArrayList<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @NonNull
    @Override
    public RecipeIngredientsAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_ingredient_item, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientsAdapter.IngredientViewHolder holder, int position) {
        holder.setData(mIngredients.get(position));
    }

    @Override
    public int getItemCount() {
        return mIngredients != null ? mIngredients.size() : 0;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name_tv) TextView mIngredientNameTV;
        @BindView(R.id.ingredient_quantity_tv) TextView mIngredientQuantityTV;
        @BindView(R.id.ingredient_unit_tv) TextView mIngredientUnitTV;
        private Ingredient mIngredient;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Ingredient ingredient) {
            mIngredient = ingredient;
            mIngredientNameTV.setText(ingredient.getName());
            mIngredientQuantityTV.setText(String.valueOf(ingredient.getQuantity()));
            mIngredientUnitTV.setText(ingredient.getMeasure());
        }
    }

}
