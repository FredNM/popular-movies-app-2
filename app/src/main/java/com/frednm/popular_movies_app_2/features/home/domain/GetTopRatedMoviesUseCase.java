package com.frednm.popular_movies_app_2.features.home.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.frednm.popular_movies_app_2.data.model.Movie;
import com.frednm.popular_movies_app_2.data.repository.MovieRepository;
import com.frednm.popular_movies_app_2.data.repository.utils.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * This UseCase is almost useless in this app, but exists for "Clean coding" purpose :)
 */

public class GetTopRatedMoviesUseCase {

    public MovieRepository repository;

    @Inject
    public GetTopRatedMoviesUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<List<Movie>>> invoke(Boolean forceRefresh){
        return Transformations.map(repository.getTopRatedMovies(forceRefresh),this::transform);
    }

    // Useless so far, but could be interesting to apply something here
    private Resource<List<Movie>> transform(Resource<List<Movie>> data) {
        return data;
    }
}
