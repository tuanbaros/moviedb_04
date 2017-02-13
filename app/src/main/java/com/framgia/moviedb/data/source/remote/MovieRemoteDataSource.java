package com.framgia.moviedb.data.source.remote;

import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.MovieDataSource;
import com.framgia.moviedb.service.ServiceGenerator;
import com.framgia.moviedb.service.movie.ApiListMovie;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source.remote
 */
public class MovieRemoteDataSource implements MovieDataSource {
    private static MovieRemoteDataSource sMovieRemoteDataSource;

    private MovieRemoteDataSource() {
    }

    public static MovieRemoteDataSource getInstance() {
        if (sMovieRemoteDataSource == null)
            return new MovieRemoteDataSource();
        return sMovieRemoteDataSource;
    }

    @Override
    public void getMovies(@Nullable String type, @Nullable String page,
                          final GetCallback getCallback) {
        ApiListMovie.ListMovies listMovies = ServiceGenerator
            .createService(ApiListMovie.ListMovies.class);
        Call<ApiListMovie.Response> call = listMovies.loadMovies(
            type, ApiListMovie.param(page));
        call.enqueue(new Callback<ApiListMovie.Response>() {
            @Override
            public void onResponse(Call<ApiListMovie.Response> call,
                                   Response<ApiListMovie.Response> response) {
                if (response != null) {
                    getCallback.onLoaded(response.body().getMovies());
                } else getCallback.onNotAvailable();
            }

            @Override
            public void onFailure(Call<ApiListMovie.Response> call, Throwable t) {
                getCallback.onNotAvailable();
            }
        });
    }

    @Override
    public void saveMovie(@Nullable String type, Movie data) {
        // not require for remote
    }

    @Override
    public void deleteAllData(@Nullable String type) {
        // not require for remote
    }

    @Override
    public boolean getFavorite(Movie data) {
        return false;
    }

    @Override
    public void updateFavorite(@Nullable String type, Movie data) {
        // not require for remote
    }

    @Override
    public void loadMovies(String type, @Nullable String idOrQuery, String page,
                           GetCallback getCallback) {
        // load movies in list screen
    }

    @Override
    public void loadFavorite(GetCallback getCallback) {
        // load favorite movies in list screen
    }
}
