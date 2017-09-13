package com.codepath.flicks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.flicks.adapters.MoviesAdapter;
import com.codepath.flicks.models.Movie;
import com.codepath.flicks.models.MoviesDbResponse;

import com.codepath.flicks.providers.DataProvider;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    MoviesAdapter adapter;
    ArrayList<Movie> movies;
    RecyclerView rvMovies;
    LinearLayoutManager mLayoutManager;

    DataProvider provider;
    private final OkHttpClient client = new OkHttpClient();
    private final String apiUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    boolean mIsLoading = true;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    int mPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        loadContent();
    }


    private void initialize() {
        // Initialization code
        provider = new DataProvider();
        movies = new ArrayList<>();

        adapter = new MoviesAdapter(this, movies);
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setAdapter(adapter);
        mLayoutManager = new LinearLayoutManager(this);
        rvMovies.setLayoutManager(mLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvMovies.addItemDecoration(itemDecoration);

        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    if(mIsLoading) {
                        return;
                    }

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount -1)
                        {
                            mIsLoading = true;
                            getNextPage();
                        }
                }
            }
        });
    }

    private void loadContent() {
        getResponse(getApiUrl(0));
        mPage++;
    }

    private void getNextPage() {
        getResponse(getApiUrl(mPage + 1));
        mPage++;
    }

    private String getApiUrl(Integer page) {
        String url = apiUrl;
        if(page > 0) {
            url += "&page=" + Integer.toString(page);
        }
        return url;
    }

    private void getResponse(String url) {
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

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movies.addAll(resultMovies);
                            adapter.notifyDataSetChanged();
                            mIsLoading = false;
                        }
                    });
                }
            }
        });
    }
}
