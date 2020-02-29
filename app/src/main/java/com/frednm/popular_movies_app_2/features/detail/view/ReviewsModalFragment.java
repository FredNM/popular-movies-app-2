package com.frednm.popular_movies_app_2.features.detail.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frednm.popular_movies_app_2.databinding.FragmentReviewsModalBinding;
import com.frednm.popular_movies_app_2.features.detail.DetailViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class ReviewsModalFragment extends BottomSheetDialogFragment  {

    private DetailViewModel viewModel;
    FragmentReviewsModalBinding binding;

    public ReviewsModalFragment(DetailViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewsModalBinding.inflate(inflater, container, false);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.configureRecyclerView();
    }

    private void configureRecyclerView() {
        binding.activityDetailReviewsRecyclerView.setAdapter(new ReviewAdapter());
    }
}
