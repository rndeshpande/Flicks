package com.codepath.flicks.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;
import com.codepath.flicks.models.MovieDetails;
import com.codepath.flicks.models.MoviesDbResponse;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MovieDetailsActivity extends AppCompatActivity {
    ImageView ivImage;
    RatingBar rbRating;
    TextView tvOverview;
    TextView tvTitle;
    TextView tvTagline;

    private final OkHttpClient client = new OkHttpClient();
    private final String apiUrl = "https://api.themoviedb.org/3/movie/";
    private final String apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    final String portraitSize = "w780";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId", -1);

        ivImage = (ImageView) findViewById(R.id.ivImage);
        rbRating = (RatingBar) findViewById(R.id.rbRating);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvTitle  = (TextView) findViewById(R.id.tvTitle);
        tvTagline  = (TextView) findViewById(R.id.tvTagline);

        showDetails(this, movieId);
    }

    private  void showDetails(final Context context, int movieId) {
        Log.d("URL", getApiUrl(movieId));
        Request request = new Request.Builder()
                .url(getApiUrl(movieId))
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

    private String getApiUrl(int movieId) {
        return  apiUrl + Integer.toString(movieId) + "?api_key=" + apiKey;
    }

    private void populateView(final Context context, final MovieDetails details) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                tvOverview.setText(details.overview);
                rbRating.setNumStars(10);
                rbRating.setRating(details.voteAverage);
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
