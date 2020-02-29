package com.frednm.popular_movies_app_2.data.local;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.frednm.popular_movies_app_2.data.model.Video;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class VideoDao extends BaseDao<Video> {

    @Query("SELECT * FROM Video WHERE movieId = :movieId AND type= :trailer") // Take only trailers
    public abstract Flowable<List<Video>> getMovieTrailers(Integer movieId, String trailer);

    @Query("DELETE FROM Video WHERE movieId = :movieId ")
    abstract void deleteSomeVideos(Integer movieId);

    //For insertion of movies list, delete before saving new results, in order to avoid having deprecated data
    @Transaction
    public void saveVideos(List<Video> videos, Integer movieId) {
        if (videos != null || !videos.isEmpty()) {
            deleteSomeVideos(movieId);
            insert(videos); // In the different videos, the movieId value has been already set to the selected movie in the MovieRepository method.
        }
    }

    public void saveVideo(Video video){
        insert(video);
    }


}
