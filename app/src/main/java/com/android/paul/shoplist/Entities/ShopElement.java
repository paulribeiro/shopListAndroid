package com.android.paul.shoplist.Entities;

import com.android.paul.shoplist.Entities.Ingredient;
import com.android.paul.shoplist.Entities.Quantity;

public class ShopElement {
    private Ingredient ingredient;
    private Quantity quantity;

    public ShopElement(Ingredient ingredient, Quantity quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
