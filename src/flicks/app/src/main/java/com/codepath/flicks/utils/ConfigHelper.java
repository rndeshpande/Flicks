package com.codepath.flicks.utils;

/**
 * Created by rdeshpan on 9/16/2017.
 */

public class ConfigHelper {
    //private static final String apiUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String baseUrlMovieDb = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY_MOVIEDB = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String API_KEY_YOUTUBE = "AIzaSyChFS5XMhK8kXSE-cecMW9ux1RKuADEBk4";

    public static String getMovieDbUrl() {
        return baseUrlMovieDb + "now_playing?api_key=" + API_KEY_MOVIEDB;
    }

    public static String getMovieDbUrlUpcoming() {
        return baseUrlMovieDb + "upcoming?api_key=" + API_KEY_MOVIEDB;
    }

    public static String getMovieDbUrlTopRated() {
        return baseUrlMovieDb + "top_rated?api_key=" + API_KEY_MOVIEDB;
    }

    public static String getMovieDbMovieUrl(int movieId) {
        return  baseUrlMovieDb + Integer.toString(movieId) + "?api_key=" + API_KEY_MOVIEDB;
    }

    public static String getMovieDbVideosUrl(int movieId) {
        return  baseUrlMovieDb + Integer.toString(movieId) + "/videos?api_key=" + API_KEY_MOVIEDB;
    }

    public  static String getYouTubeApiKey() {
        return API_KEY_YOUTUBE;
    }
}
