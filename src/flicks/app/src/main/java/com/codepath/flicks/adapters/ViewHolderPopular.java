package com.codepath.flicks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flicks.R;

/**
 * Created by rdeshpan on 9/12/2017.
 */

public class ViewHolderPopular extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView ivImagePopular;

    public ViewHolderPopular(View itemView) {
        super(itemView);

        ivImagePopular = (ImageView) itemView.findViewById(R.id.ivImagePopular);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition(); // gets item position
    }
}
