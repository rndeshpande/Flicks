package com.codepath.flicks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rdeshpan on 9/12/2017.
 */

public class ViewHolderRegular extends RecyclerView.ViewHolder {

    @BindView(R.id.ivImage) public ImageView ivImage;
    @BindView(R.id.tvTitle) public TextView tvTitle;
    @BindView(R.id.tvDescription) public TextView tvDescription;

    public ViewHolderRegular(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
