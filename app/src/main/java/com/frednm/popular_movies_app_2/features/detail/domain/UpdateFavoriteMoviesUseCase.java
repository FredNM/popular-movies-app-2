package com.frednm.popular_movies_app_2.features.detail.domain;

import com.frednm.popular_movies_app_2.data.repository.MovieRepository;

import javax.inject.Inject;


public class UpdateFavoriteMoviesUseCase {


    public MovieRepository repository;

    @Inject
    public UpdateFavoriteMoviesUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    public void invoke(Boolean isSelected, Integer movieId) {
        repository.updateFavoriteMovies(isSelected, movieId);
    }
}
