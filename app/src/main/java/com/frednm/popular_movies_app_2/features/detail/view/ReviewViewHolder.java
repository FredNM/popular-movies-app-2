package com.frednm.popular_movies_app_2.features.detail.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.popular_movies_app_2.data.model.Review;
import com.frednm.popular_movies_app_2.databinding.ItemReviewBinding;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private ItemReviewBinding binding;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ItemReviewBinding.bind(itemView);
    }

    protected void bindTo(Review review) {
        binding.setReview(review);
    }
}
