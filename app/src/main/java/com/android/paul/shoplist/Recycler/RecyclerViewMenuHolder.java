package com.android.paul.shoplist.Recycler;

import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.paul.shoplist.R;

public class RecyclerViewMenuHolder extends RecyclerView.ViewHolder{
    public TextView menuTextView;
    public ImageView imageView;
    public Button buttonYes;
    public Button buttonNo;

    public RecyclerViewMenuHolder(View itemView) {
        super(itemView);
        menuTextView = (TextView)itemView.findViewById(R.id.MenuText);
        imageView = (ImageView)itemView.findViewById(R.id.imageView2);
        buttonYes = (Button)itemView.findViewById(R.id.buttonYes);
        buttonNo = (Button)itemView.findViewById(R.id.buttonNo);

    }
}