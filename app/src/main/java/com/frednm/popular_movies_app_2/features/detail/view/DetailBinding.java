package com.frednm.popular_movies_app_2.features.detail.view;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.frednm.popular_movies_app_2.data.model.Review;
import com.frednm.popular_movies_app_2.data.model.Video;

import java.util.List;

public class DetailBinding {

    @BindingAdapter("isSelected")
    public static void setSelected(View view, Boolean isUserFavorite) { // set View State
        if (isUserFavorite != null) {
            Log.d("DataBinding", "isUserFavorite: "+isUserFavorite);
            view.setSelected(isUserFavorite);
        }
    }

    @BindingAdapter("itemsVideo")
    public static void setItemsVideo (RecyclerView recyclerView, @NonNull List<Video> videos) {
        if(videos != null) {
            if (recyclerView.getAdapter() instanceof VideoAdapter) {
                ((VideoAdapter) recyclerView.getAdapter()).updateData(videos);
            }
        } else {
            Log.d("DataBinding", "No videos ");
        }
    }

    @BindingAdapter("itemsReview")
    public static void setItemsReview (RecyclerView recyclerView, @NonNull List<Review> reviews) {
        if(reviews != null) {
            if (recyclerView.getAdapter() instanceof ReviewAdapter) {
                ((ReviewAdapter) recyclerView.getAdapter()).updateData(reviews);
            }
        } else {
            Log.d("DataBinding", "No reviews ");
        }
    }
}
