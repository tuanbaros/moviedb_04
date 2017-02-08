package com.framgia.moviedb.feature.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.moviedb.R;
import com.framgia.moviedb.data.model.Genre;

public class MoviesActivity extends AppCompatActivity {
    public static final String EXTRA_GENRE = "EXTRA_GENRE";
    public static final String EXTRA_KEY = "EXTRA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }

    public static Intent getMoviesIntent(Context context, String key, Genre genre) {
        Intent intent = new Intent(context, MoviesActivity.class);
        intent.putExtra(MoviesActivity.EXTRA_GENRE, genre);
        intent.putExtra(MoviesActivity.EXTRA_KEY, key);
        return intent;
    }
}
