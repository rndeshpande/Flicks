package com.codepath.flicks.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rdeshpan on 9/10/2017.
 */

public class MoviesDbResponse {
    @SerializedName("results")
    public  ArrayList<Movie> movies;

    @SerializedName("page")
    public  Integer page;

    @SerializedName("total_results")
    public  Integer totalResults;

    @SerializedName("total_pages")
    public  Integer totalPages;
}
