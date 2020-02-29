package com.frednm.popular_movies_app_2.features.detail.domain;

import androidx.lifecycle.LiveData;

import com.frednm.popular_movies_app_2.data.model.Movie;
import com.frednm.popular_movies_app_2.data.repository.MovieRepository;
import com.frednm.popular_movies_app_2.data.repository.utils.Resource;

import javax.inject.Inject;


/**
 * This UseCase is almost useless in this app, but exists for "Clean coding" purpose :)
 */
public class GetDetailMovieUseCase {

    public MovieRepository repository;

    @Inject
    public GetDetailMovieUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<Movie>> invoke(Boolean forceRefresh, Integer movieId){
        repository.getReviewsMovie(forceRefresh, movieId); // execute this to fill Review table, the reviews associated to a Movie will be added to Review table
        return repository.getDetailMovie(forceRefresh, movieId); // then execute DetailMovie and thus fill Video table, having Videos (trailers) of a movie
    }


}
