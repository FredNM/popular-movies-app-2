package com.frednm.popular_movies_app_2.features.detail.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.popular_movies_app_2.R;
import com.frednm.popular_movies_app_2.data.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private List<Review> reviews = new ArrayList<>();

    public ReviewAdapter() { }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bindTo(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void updateData(List<Review> items) {
        ReviewItemDiffCallBack diffCallback = new ReviewItemDiffCallBack(reviews, items);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        reviews.clear();
        reviews.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }
}
