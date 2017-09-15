package com.codepath.flicks.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flicks.R;
import com.codepath.flicks.activities.MovieDetailsActivity;
import com.codepath.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by rdeshpan on 9/9/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Movie> mMovies;
    private Context mContext;

    public static final int POPULAR = 1;
    public static final int REGULAR = 2;

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        mContext = context;
        mMovies= movies;
    }

    @Override
    public int getItemViewType(int position) {
        Movie movie = mMovies.get(position);

        if (movie.voteAverage >= 5) {
            return POPULAR;
        } else {
            return  REGULAR;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder viewHolder;
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case POPULAR:
                View itemViewPopular = inflater.inflate(R.layout.list_movie_popular, parent, false);

                viewHolder = new ViewHolderPopular(itemViewPopular);
                itemViewPopular.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAdapterPosition();
                        Movie movie = mMovies.get(position);
                        showDetails(context, movie.id);
                    }
                });
                break;
            default:;
                View itemViewRegular = inflater.inflate(R.layout.list_movie, parent, false);
                viewHolder = new ViewHolderRegular(itemViewRegular);
                itemViewRegular.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getAdapterPosition();
                        Movie movie = mMovies.get(position);
                        showDetails(context, movie.id);
                    }
                });
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case POPULAR:
                ViewHolderPopular viewHolderPopular = (ViewHolderPopular) viewHolder;
                configureViewHolderPopular(viewHolderPopular, position);
                break;
            default:
                ViewHolderRegular viewHolderRegular = (ViewHolderRegular) viewHolder;
                configureViewHolderRegular(viewHolderRegular, position);
                break;
        }

    }

    private void configureViewHolderRegular(ViewHolderRegular viewHolder, int position) {
        final String portraitSize = "w342";
        final String landscapeSize = "w780";

        Movie movie = mMovies.get(position);

        final ImageView ivImage = viewHolder.ivImage;
        final Context context = viewHolder.ivImage.getContext();

        TextView tvTitle = viewHolder.tvTitle;
        tvTitle.setText(movie.title);

        TextView tvDescription = viewHolder.tvDescription;
        tvDescription.setText(movie.overview);

        final String imagePath = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                movie.getPosterPath(portraitSize) :
                movie.getBackdropPath(landscapeSize);

        configurePicasso(context, imagePath, ivImage);
    }

    private void configureViewHolderPopular(ViewHolderPopular viewHolder, int position) {
        final String portraitSize = "w780";
        final String landscapeSize = "w1280";

        Movie movie = mMovies.get(position);

        final ImageView ivImagePopular = viewHolder.ivImagePopular;
        final Context context = viewHolder.ivImagePopular.getContext();

        final String imagePath = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                movie.getBackdropPath(portraitSize) :
                movie.getBackdropPath(landscapeSize);

        configurePicasso(context, imagePath, ivImagePopular);
    }


    private void configurePicasso(Context context, String imagePath, ImageView imageView) {
        Picasso.with(context)
                .load(imagePath)
                .placeholder(R.drawable.camera)
                .transform(new RoundedCornersTransformation(50,10))
                .into(imageView);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    private void showDetails(Context context, int id) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra("movieId", id);
        context.startActivity(intent);
    }
}
