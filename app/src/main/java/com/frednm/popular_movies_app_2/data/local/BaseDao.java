package com.frednm.popular_movies_app_2.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insert(List<T> data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insert(T data);

}
