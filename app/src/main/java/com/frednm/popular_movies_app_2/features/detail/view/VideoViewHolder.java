package com.frednm.popular_movies_app_2.features.detail.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.popular_movies_app_2.data.model.Video;

import com.frednm.popular_movies_app_2.databinding.ItemVideoBinding;
import com.frednm.popular_movies_app_2.features.detail.DetailViewModel;

public class VideoViewHolder extends RecyclerView.ViewHolder {

    private ItemVideoBinding binding;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemVideoBinding.bind(itemView);
    }

    protected void bindTo(Video video, DetailViewModel viewModel) {
        binding.setVideo(video);
        binding.setViewmodel(viewModel);
    }
}
