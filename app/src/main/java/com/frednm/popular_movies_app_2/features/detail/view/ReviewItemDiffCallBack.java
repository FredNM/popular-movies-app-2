package com.frednm.popular_movies_app_2.features.detail.view;

import androidx.recyclerview.widget.DiffUtil;

import com.frednm.popular_movies_app_2.data.model.Review;

import java.util.List;

public class ReviewItemDiffCallBack extends DiffUtil.Callback {

    private List<Review> oldList;
    private List<Review> newList;

    public ReviewItemDiffCallBack(List<Review> oldList, List<Review> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getMovieId()== newList.get(newItemPosition).getMovieId()
                && oldList.get(oldItemPosition).getAuthor()== newList.get(newItemPosition).getAuthor()
                && oldList.get(oldItemPosition).getContent()== newList.get(newItemPosition).getContent();
    }
}
