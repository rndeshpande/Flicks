package com.codepath.flicks.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;
import com.codepath.flicks.models.MovieDetails;
import com.codepath.flicks.models.MoviesDbResponse;
import com.codepath.flicks.utils.ConfigHelper;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MovieDetailsActivity extends AppCompatActivity {
    @BindView(R.id.ivImage) ImageView ivImage;
    @BindView(R.id.rbRating) RatingBar rbRating;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvTagline) TextView tvTagline;
    @BindView(R.id.ivPlay) ImageView ivPlay;

    private final OkHttpClient client = new OkHttpClient();
    final String portraitSize = "w780";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final int movieId = intent.getIntExtra("movieId", -1);

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MoviePlayerActivity.class);
                intent.putExtra("movieId", movieId);
                v.getContext().startActivity(intent);
            }
        });

        showDetails(this, movieId);
    }

    private  void showDetails(final Context context, int movieId) {
        Request request = new Request.Builder()
                .url(ConfigHelper.getMovieDbMovieUrl(movieId))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    final MovieDetails details = gsonBuilder.create().fromJson(responseBody.string(), MovieDetails.class);
                    populateView(context, details);
                }
            }
        });

    }

    private void populateView(final Context context, final MovieDetails details) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                tvOverview.setText(details.overview);
                rbRating.setNumStars(10);
                rbRating.setRating(details.voteAverage);
                LayerDrawable stars = (LayerDrawable) rbRating.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                tvTitle.setText(details.title);
                tvTagline.setText(details.tagline);

                Picasso.with(context)
                        .load(details.getBackdropPath(portraitSize))
                        .placeholder(R.drawable.camera)
                        .transform(new RoundedCornersTransformation(50,10))
                        .into(ivImage);
            }
        });
    }
}
