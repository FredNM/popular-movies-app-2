package com.frednm.popular_movies_app_2.features.detail.view;

import androidx.recyclerview.widget.DiffUtil;

import com.frednm.popular_movies_app_2.data.model.Video;

import java.util.List;

public class VideoItemDiffCallBack extends DiffUtil.Callback {

    private List<Video> oldList;
    private List<Video> newList;

    public VideoItemDiffCallBack(List<Video> oldList, List<Video> newList) {
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
                && oldList.get(oldItemPosition).getName()== newList.get(newItemPosition).getName();
    }
}
