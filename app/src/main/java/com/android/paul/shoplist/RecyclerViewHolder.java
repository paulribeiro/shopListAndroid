package com.android.paul.shoplist;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder{
    public TextView ingredientTextView;
    public TextView quantityTextView;
    public ConstraintLayout viewBackground, viewForeground;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        ingredientTextView = (TextView)itemView.findViewById(R.id.IngredientText);
        quantityTextView = itemView.findViewById(R.id.QuantityText);
        viewBackground = itemView.findViewById(R.id.backgroundView);
        viewForeground = itemView.findViewById(R.id.forgroundView);
    }
}