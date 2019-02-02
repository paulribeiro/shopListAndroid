package com.android.paul.shoplist.Recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.paul.shoplist.Entities.MenuElement;
import com.android.paul.shoplist.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewMenuAdapter extends RecyclerView.Adapter<RecyclerViewMenuHolder>{

    private List<MenuElement> menuList;

    public RecyclerViewMenuAdapter(List<MenuElement> menuList) {
        this.menuList = menuList;
    }

    public RecyclerViewMenuAdapter() {
        this.menuList = new ArrayList<MenuElement>();
    }

    public List<MenuElement> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuElement> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public RecyclerViewMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.menu_cell, parent, false);
        return new RecyclerViewMenuHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMenuHolder holder, int position) {
        holder.menuTextView.setText(menuList.get(position).getTitle());
        //holder.imageView.setImageDrawable();
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public void removeItem(int position) {
        menuList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(MenuElement item, int position) {
        menuList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public void addItem(MenuElement item)
    {
        menuList.add(item);
        notifyDataSetChanged();
    }

    public MenuElement getItem(int position)
    {
        return menuList.get(position);
    }
}
