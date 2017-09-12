package com.codepath.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.R.attr.width;
import static com.codepath.flicks.R.attr.height;

/**
 * Created by rdeshpan on 9/9/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private ArrayList<Movie> mMovies;
    private Context mContext;
    private final Integer HORIZONTAL = 1;

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        mContext = context;
        mMovies= movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivImage;
        public TextView tvTitle;
        public TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
        }
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.list_movie, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder viewHolder, int position) {
        Movie movie = mMovies.get(position);

        ImageView ivImage = viewHolder.ivImage;
        Context context = viewHolder.ivImage.getContext();

        String imagePath = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                movie.getPosterPath() :
                movie.getBackdropPath();

        Picasso.with(context)
                .load(imagePath)
                .transform(new RoundedCornersTransformation(50,10))
                .into(ivImage);

        Log.d("APP",movie.getPosterPath());

        TextView tvTitle = viewHolder.tvTitle;
        tvTitle.setText(movie.title);

        TextView tvDescription = viewHolder.tvDescription;
        tvDescription.setText(movie.overview);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
