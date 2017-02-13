package com.framgia.moviedb.feature.main;

import com.framgia.moviedb.data.model.Genre;
import com.framgia.moviedb.data.model.Movie;
import com.framgia.moviedb.data.source.DataSource;
import com.framgia.moviedb.data.source.GenreDataSource;
import com.framgia.moviedb.data.source.MovieDataSource;
import com.framgia.moviedb.data.source.MovieRepository;
import com.framgia.moviedb.service.movie.ApiListMovie;

import java.util.List;

/**
 * Created by tuannt on 06/02/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.feature.splash
 */
public class MainPresenter implements
    MainContract.Presenter {
    private MainContract.View mMainView;
    private GenreDataSource mGenreRepository;
    private MovieDataSource mMovieRepository;
    private int mState = 0;
    private static final int GET_DATA_DONE = 5;
    private boolean mIsRefresh;

    public MainPresenter(MainContract.View mainView,
                         GenreDataSource genreRepository,
                         MovieDataSource movieRepository) {
        mMainView = mainView;
        mGenreRepository = genreRepository;
        mMovieRepository = movieRepository;
    }

    @Override
    public void start() {
        mMainView.onPrepare();
        loadData(MovieRepository.FIRST_PAGE);
    }

    @Override
    public void loadGenres() {
        mGenreRepository.getGenres(mIsRefresh && mIsRefresh,
            new DataSource.GetCallback<Genre>() {
                @Override
                public void onLoaded(List<Genre> datas) {
                    mMainView.onGenresLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    if (!mIsRefresh) mMainView.onError();
                    checkState();
                }
            });
    }

    @Override
    public void loadNowPlayingMovies(String page) {
        mMovieRepository.getMovies(ApiListMovie.NOW_PLAYING, page,
            new DataSource.GetCallback<Movie>() {
                @Override
                public void onLoaded(List<Movie> datas) {
                    mMainView.onNowPlayingMoviesLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    if (!mIsRefresh) mMainView.onError();
                    checkState();
                }
            });
    }

    @Override
    public void loadPopularMovies(String page) {
        mMovieRepository.getMovies(ApiListMovie.POPULAR, page,
            new DataSource.GetCallback<Movie>() {
                @Override
                public void onLoaded(List<Movie> datas) {
                    mMainView.onPopularMoviesLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    if (!mIsRefresh) mMainView.onError();
                    checkState();
                }
            });
    }

    @Override
    public void loadTopRatedMovies(String page) {
        mMovieRepository.getMovies(ApiListMovie.TOP_RATED, page,
            new DataSource.GetCallback<Movie>() {
                @Override
                public void onLoaded(List<Movie> datas) {
                    mMainView.onTopRatedMoviesLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    if (!mIsRefresh) mMainView.onError();
                    checkState();
                }
            });
    }

    @Override
    public void loadUpComingMovies(String page) {
        mMovieRepository.getMovies(ApiListMovie.UPCOMING, page,
            new DataSource.GetCallback<Movie>() {
                @Override
                public void onLoaded(List<Movie> datas) {
                    mMainView.onUpComingMoviesLoaded(datas);
                    checkState();
                }

                @Override
                public void onNotAvailable() {
                    if (!mIsRefresh) mMainView.onError();
                    checkState();
                }
            });
    }

    @Override
    public void checkState() {
        mState++;
        if (mState == GET_DATA_DONE) {
            mIsRefresh = false;
            mMainView.onSuccess();
            mMainView.onRefreshDone();
        }
    }

    @Override
    public void openGenreDetails(Genre genre) {
        mMainView.showGenreDetailsUi(genre);
    }

    @Override
    public void openMovieDetails(Movie movie) {
        mMainView.showMovieDetailsUi(movie);
    }

    @Override
    public void openListDetails(String type) {
        mMainView.showListDetailsUi(type);
    }

    @Override
    public void refresh() {
        mIsRefresh = true;
        loadData(MovieRepository.REFRESH_PAGE);
    }

    private void loadData(String page) {
        mState = 0;
        loadGenres();
        loadNowPlayingMovies(page);
        loadPopularMovies(page);
        loadTopRatedMovies(page);
        loadUpComingMovies(page);
    }
}
