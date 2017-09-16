package com.codepath.flicks.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rdeshpan on 9/16/2017.
 */

public class MovieVideo {
    @SerializedName("id")
    public  String id;

    @SerializedName("key")
    public  String key;

    @SerializedName("name")
    public  String name;

    @SerializedName("site")
    public  String site;

    @SerializedName("type")
    public  String type;
}
