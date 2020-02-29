package com.frednm.popular_movies_app_2.data.local;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;


import com.frednm.popular_movies_app_2.data.model.Review;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class ReviewDao extends BaseDao<Review> {

    @Query("SELECT * FROM Review WHERE movieId = :movieId")
    public abstract Flowable<List<Review>> getMovieReviews(Integer movieId);

    @Query("DELETE FROM Review WHERE movieId = :movieId ")
    abstract void deleteSomeReviews(Integer movieId);

    //For insertion of reviews list, delete before saving new results, in order to avoid having deprecated data
    @Transaction
    public void saveReviews(List<Review> reviews, Integer movieId) {
        if (reviews != null || !reviews.isEmpty()) {
            deleteSomeReviews(movieId);
            insert(reviews); // In the different videos, the movieId value has been already set to the selected movie in the MovieRepository method.
        }
    }
}
