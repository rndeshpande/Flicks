package com.codepath.flicks.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.flicks.R;
import com.codepath.flicks.adapters.MoviesAdapter;
import com.codepath.flicks.models.Movie;
import com.codepath.flicks.models.MoviesDbResponse;

import com.codepath.flicks.utils.ConfigHelper;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MoviesActivity extends AppCompatActivity {

    @BindView(R.id.rvMovies) RecyclerView rvMovies;
    @BindView(R.id.rvComingSoon) RecyclerView rvUpcoming;
    @BindView(R.id.rvTopRated) RecyclerView rvTopRated;

    MoviesAdapter adapter;
    MoviesAdapter adapterUp;
    MoviesAdapter adapterTr;

    ArrayList<Movie> mMovies;
    ArrayList<Movie> mMoviesUp;
    ArrayList<Movie> mMoviesTr;

    LinearLayoutManager mLayoutManager;
    LinearLayoutManager mLayoutManagerUp;
    LinearLayoutManager mLayoutManagerTr;

    private final OkHttpClient client = new OkHttpClient();

    boolean mIsLoading = true;
    boolean mIsLoadingUp = true;
    boolean mIsLoadingTr = true;
    int mPage = 0;
    int mPageUp = 0;
    int mPageTr = 0;

    private final int CATEGORY_NOW_PLAYING = 1;
    private final int CATEGORY_UPCOMING = 2;
    private final int CATEGORY_TOP_RATED = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initialize();
        loadContent(CATEGORY_NOW_PLAYING);
        loadContent(CATEGORY_UPCOMING);
        loadContent(CATEGORY_TOP_RATED);
    }


    private void initialize() {
        initializeNowPlaying();
        initializeUpcoming();
        initializeTopRated();
    }

    private void initializeNowPlaying() {
        // Initialization code
        mMovies = new ArrayList<>();

        adapter = new MoviesAdapter(this, mMovies);

        rvMovies.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rvMovies.setLayoutManager(mLayoutManager);

        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dx > 0) //check for scroll right
                {
                    if(mIsLoading) {
                        return;
                    }

                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount -1)
                    {
                        mIsLoading = true;
                        getNextPage(CATEGORY_NOW_PLAYING);
                    }
                }
            }
        });
    }

    private void initializeUpcoming() {
        // Initialization code
        mMoviesUp = new ArrayList<>();

        adapterUp = new MoviesAdapter(this, mMoviesUp);

        rvUpcoming.setAdapter(adapterUp);
        mLayoutManagerUp = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rvUpcoming.setLayoutManager(mLayoutManagerUp);

        rvUpcoming.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dx > 0) //check for scroll right
                {
                    if(mIsLoading) {
                        return;
                    }

                    int visibleItemCount = mLayoutManagerUp.getChildCount();
                    int totalItemCount = mLayoutManagerUp.getItemCount();
                    int pastVisibleItems = mLayoutManagerUp.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount -1)
                    {
                        mIsLoading = true;
                        getNextPage(CATEGORY_UPCOMING);
                    }
                }
            }
        });
    }

    private void initializeTopRated() {
        // Initialization code
        mMoviesTr = new ArrayList<>();

        adapterTr = new MoviesAdapter(this, mMoviesTr);

        rvTopRated.setAdapter(adapterTr);
        mLayoutManagerTr = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rvTopRated.setLayoutManager(mLayoutManagerTr);

        rvTopRated.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dx > 0) //check for scroll right
                {
                    if(mIsLoading) {
                        return;
                    }

                    int visibleItemCount = mLayoutManagerTr.getChildCount();
                    int totalItemCount = mLayoutManagerTr.getItemCount();
                    int pastVisibleItems = mLayoutManagerTr.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount -1)
                    {
                        mIsLoading = true;
                        getNextPage(CATEGORY_TOP_RATED);
                    }
                }
            }
        });
    }

    private void loadContent(int category) {
        getResponse(getApiUrl(0, category), category);

        switch (category) {
            case CATEGORY_NOW_PLAYING:
                mPage++;
                break;
            case CATEGORY_UPCOMING:
                mPageUp++;
                break;
            case CATEGORY_TOP_RATED:
                mPageTr++;
                break;
            default:
                break;
        }
    }

    private void getNextPage(int category) {
        switch (category) {
            case CATEGORY_NOW_PLAYING:
                getResponse(getApiUrl(mPage + 1,category), category);
                mPage++;
                break;
            case CATEGORY_UPCOMING:
                getResponse(getApiUrl(mPageUp + 1,category),category);
                mPageUp++;
                break;
            case CATEGORY_TOP_RATED:
                getResponse(getApiUrl(mPageUp + 1,category),category);
                mPageTr++;
                break;
            default:
                break;
        }
    }

    private String getApiUrl(int page, int category) {
        String url ="";
        switch (category) {
            case CATEGORY_NOW_PLAYING:
                url = ConfigHelper.getMovieDbUrl();
                break;
            case CATEGORY_UPCOMING:
                url = ConfigHelper.getMovieDbUrlUpcoming();
                break;
            case CATEGORY_TOP_RATED:
                url = ConfigHelper.getMovieDbUrlTopRated();
                break;
            default:
                break;
        }

        if(page > 0) {
            url += "&page=" + Integer.toString(page);
        }
        return url;
    }

    private void getResponse(String url, final int category) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    final MoviesDbResponse dbResponse = gsonBuilder.create().fromJson(responseBody.string(), MoviesDbResponse.class);
                    final ArrayList<Movie> resultMovies = dbResponse.movies;

                    MoviesActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateDataset(resultMovies, category);
                        }
                    });
                }
            }
        });
    }

    private void updateDataset(ArrayList<Movie> movies, int category) {
        switch (category) {
            case CATEGORY_NOW_PLAYING:
                mMovies.addAll(movies);
                adapter.notifyDataSetChanged();
                mIsLoading = false;
                break;
            case CATEGORY_UPCOMING:
                mMoviesUp.addAll(movies);
                adapterUp.notifyDataSetChanged();
                mIsLoadingUp = false;
                break;
            case CATEGORY_TOP_RATED:
                mMoviesTr.addAll(movies);
                adapterTr.notifyDataSetChanged();
                mIsLoadingTr = false;
                break;
            default:
                break;
        }

    }
}
