package com.codepath.flicks.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rdeshpan on 9/16/2017.
 */

public class MovieVideosResponse {

    @SerializedName("id")
    public  Integer id;

    @SerializedName("results")
    public ArrayList<MovieVideo> videos;
}
