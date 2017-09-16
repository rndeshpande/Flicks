package com.codepath.flicks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flicks.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rdeshpan on 9/12/2017.
 */

public class ViewHolderPopular extends RecyclerView.ViewHolder {

    @BindView(R.id.ivImagePopular) public ImageView ivImagePopular;
    @BindView(R.id.ivPlay) public ImageView ivPlay;

    public ViewHolderPopular(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
