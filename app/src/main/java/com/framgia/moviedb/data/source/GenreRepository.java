package com.framgia.moviedb.data.source;

import android.content.Context;
import android.support.annotation.Nullable;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.source.local.GenreLocalDataSource;
import com.framgia.moviedb.data.source.remote.GenreRemoteDataSource;

import java.util.List;

/**
 * Created by tuannt on 03/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.data.source
 */
public class GenreRepository implements DataSource<Genre> {
    private static GenreRepository sGenreRepository;
    private DataSource mGenreRemoteDataSource;
    private DataSource mGenreLocalDataSource;

    public static GenreRepository getInstance(Context context) {
        if (sGenreRepository == null)
            return new GenreRepository(GenreRemoteDataSource.getInstance(),
                GenreLocalDataSource.getInstance(context));
        return sGenreRepository;
    }

    private GenreRepository(GenreRemoteDataSource genreRemoteDataSource,
                            GenreLocalDataSource genreLocalDataSource) {
        mGenreLocalDataSource = genreLocalDataSource;
        mGenreRemoteDataSource = genreRemoteDataSource;
    }

    @Override
    public void getDatas(@Nullable final String type, @Nullable final String page,
                         final GetCallback getCallback) {
        mGenreLocalDataSource.getDatas(type, page, new GetCallback<Genre>() {
            @Override
            public void onLoaded(List<Genre> datas) {
                getCallback.onLoaded(datas);
            }

            @Override
            public void onNotAvailable() {
                mGenreRemoteDataSource.getDatas(type, page, new GetCallback<Genre>() {
                    @Override
                    public void onLoaded(List<Genre> datas) {
                        getCallback.onLoaded(datas);
                    }

                    @Override
                    public void onNotAvailable() {
                        getCallback.onNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveData(@Nullable String type, Genre data) {
        // TODO: add data to model
    }
}
