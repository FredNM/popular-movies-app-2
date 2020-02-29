package com.frednm.popular_movies_app_2.features.detail.domain;

import com.frednm.popular_movies_app_2.data.model.Review;
import com.frednm.popular_movies_app_2.data.model.Video;
import com.frednm.popular_movies_app_2.data.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class GetReactiveMovieDetailUseCase {

    public MovieRepository repository;

    @Inject
    public GetReactiveMovieDetailUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    public Flowable<List<Video>> getMovieTrailers(Integer movieId) {
        return repository.getMovieTrailers(movieId);
    }

    public Flowable<List<Review>> getMovieReviews (Integer movieId) {
        return repository.getMovieReviews(movieId);
    }

}
