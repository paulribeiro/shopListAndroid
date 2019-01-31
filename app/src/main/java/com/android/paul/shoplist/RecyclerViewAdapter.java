package com.android.paul.shoplist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.paul.shoplist.Entities.ShopElement;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<ShopElement> shopList;

    RecyclerViewAdapter(List<ShopElement> shopList) {
        this.shopList = shopList;
    }

    RecyclerViewAdapter() {
        this.shopList = new ArrayList<ShopElement>();
    }

    public List<ShopElement> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopElement> shopList) {
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.ingredient_cell, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.ingredientTextView.setText(shopList.get(position).getIngredient().getName());
        String quantityString = shopList.get(position).getQuantity().getNumber() + shopList.get(position).getQuantity().getUnit();
        holder.quantityTextView.setText(quantityString);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public void removeItem(int position) {
        shopList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(ShopElement item, int position) {
        shopList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public void addItem(ShopElement item)
    {
        shopList.add(item);
        notifyDataSetChanged();
    }

    public ShopElement getItem(int position)
    {
        return shopList.get(position);
    }
}

