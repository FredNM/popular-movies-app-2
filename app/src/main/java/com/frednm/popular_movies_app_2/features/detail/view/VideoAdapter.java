package com.frednm.popular_movies_app_2.features.detail.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.popular_movies_app_2.R;
import com.frednm.popular_movies_app_2.data.model.Video;
import com.frednm.popular_movies_app_2.features.detail.DetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private List<Video> videos = new ArrayList<>();
    private DetailViewModel viewModel;

    public VideoAdapter(DetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bindTo(videos.get(position), viewModel);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void updateData(List<Video> items) {
        VideoItemDiffCallBack diffCallback = new VideoItemDiffCallBack(videos, items);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        videos.clear();
        videos.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }
}
