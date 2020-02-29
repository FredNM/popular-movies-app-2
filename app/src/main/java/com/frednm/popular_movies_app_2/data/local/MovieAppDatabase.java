package com.frednm.popular_movies_app_2.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.frednm.popular_movies_app_2.data.model.Movie;
import com.frednm.popular_movies_app_2.data.model.Review;
import com.frednm.popular_movies_app_2.data.model.Video;

@Database(entities = {Movie.class, Video.class, Review.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MovieAppDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MovieAppDatabase INSTANCE;

    // --- DAO ---
    public abstract MovieDao movieDao();

    public abstract VideoDao videoDao();

    public abstract ReviewDao reviewDao();

}
