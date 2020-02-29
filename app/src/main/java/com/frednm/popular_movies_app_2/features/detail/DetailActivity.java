package com.frednm.popular_movies_app_2.features.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.frednm.popular_movies_app_2.R;
import com.frednm.popular_movies_app_2.common.base.BaseActivity;
import com.frednm.popular_movies_app_2.common.base.BaseViewModel;
import com.frednm.popular_movies_app_2.databinding.ActivityDetailBinding;
import com.frednm.popular_movies_app_2.features.detail.utils.TextCreator;
import com.frednm.popular_movies_app_2.features.detail.view.ReviewsModalFragment;
import com.frednm.popular_movies_app_2.features.detail.view.VideosModalFragment;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity implements DetailViewModel.Listeners {

    @Inject ViewModelProvider.Factory viewModelFactory;
    private DetailViewModel viewModel;

    ActivityDetailBinding binding;
    private TextCreator textCreator = new TextCreator();
    public static final String EXTRA_MOVIEID = "extra_movieId";
    private static final int DEFAULT_MOVIEID = -1;
    private static final String MODAL_REVIEW = "Modal_Review";
    private static final String MODAL_VIDEO = "Modal_Video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DetailViewModel.class);
        viewModel.implementedListenerDetailViewModel(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);
        binding.setTextCreator(textCreator);

        Integer movieId = getIntent().getIntExtra(EXTRA_MOVIEID, DEFAULT_MOVIEID);
        viewModel.loadDataWhenActivityStarts(movieId);
    }

    public BaseViewModel getViewModel() {
        return viewModel ;
    }


    @Override
    public void openYouTube(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else{
            //Page not found
        }
    }

    @Override
    public void openReviewModalBottomSheet() {
        (new ReviewsModalFragment(viewModel)).show(getSupportFragmentManager(),MODAL_REVIEW);
    }

    @Override
    public void openVideoModalBottomSheet() {
        (new VideosModalFragment(viewModel)).show(getSupportFragmentManager(),MODAL_VIDEO);
    }
}
