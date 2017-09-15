package com.codepath.flicks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;

/**
 * Created by rdeshpan on 9/12/2017.
 */

public class ViewHolderRegular extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView ivImage;
    public TextView tvTitle;
    public TextView tvDescription;

    public ViewHolderRegular(View itemView) {
        super(itemView);

        ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();

        Toast.makeText(view.getContext() , Integer.toString(position), Toast.LENGTH_SHORT).show();
    }
}
