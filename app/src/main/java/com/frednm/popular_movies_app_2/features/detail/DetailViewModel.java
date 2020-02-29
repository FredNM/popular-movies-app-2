package com.frednm.popular_movies_app_2.features.detail;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.frednm.popular_movies_app_2.R;
import com.frednm.popular_movies_app_2.common.base.BaseViewModel;
import com.frednm.popular_movies_app_2.common.utils.Event;
import com.frednm.popular_movies_app_2.data.model.Movie;
import com.frednm.popular_movies_app_2.data.model.Review;
import com.frednm.popular_movies_app_2.data.model.Video;
import com.frednm.popular_movies_app_2.data.repository.utils.Resource;
import com.frednm.popular_movies_app_2.features.detail.domain.UpdateFavoriteMoviesUseCase;
import com.frednm.popular_movies_app_2.features.detail.domain.GetDetailMovieUseCase;
import com.frednm.popular_movies_app_2.features.detail.domain.GetReactiveMovieDetailUseCase;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.ResourceSubscriber;


public class DetailViewModel extends BaseViewModel {

    public interface Listeners {
        void openYouTube(String url);
        void openReviewModalBottomSheet();
        void openVideoModalBottomSheet();
    }
    private WeakReference<DetailViewModel.Listeners> callback;

    // FOR DATA SHOWN ON UI
    private MediatorLiveData<Movie> _movie = new MediatorLiveData<>();
    public LiveData<Movie> movie = _movie;
    private LiveData<Resource<Movie>> movieSource = new MutableLiveData<>();

    private MutableLiveData<List<Video>> _videos = new MutableLiveData<>();
    public LiveData<List<Video>> videos = _videos;

    private MutableLiveData<List<Review>> _reviews = new MutableLiveData<>();
    public LiveData<List<Review>> reviews = _reviews;

    private Integer movieId;
    private Boolean favoriteState;

    // SOME SETTING PARAMETERS
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    private GetDetailMovieUseCase getDetailMovieUseCase;
    private GetReactiveMovieDetailUseCase getReactiveMovieDetailUseCase;
    private UpdateFavoriteMoviesUseCase updateFavoriteMoviesUseCase;

    private CompositeDisposable disposableTrailers;
    private CompositeDisposable disposableReviews;

    // --- CONSTRUCTOR
    @Inject
    public DetailViewModel(GetDetailMovieUseCase getDetailMovieUseCase, GetReactiveMovieDetailUseCase getReactiveMovieDetailUseCase, UpdateFavoriteMoviesUseCase updateFavoriteMoviesUseCase) {
        this.getDetailMovieUseCase = getDetailMovieUseCase;
        this.getReactiveMovieDetailUseCase = getReactiveMovieDetailUseCase;
        this.updateFavoriteMoviesUseCase = updateFavoriteMoviesUseCase;
        this.disposableTrailers = new CompositeDisposable() ;
        this.disposableReviews = new CompositeDisposable() ;
    }

    //  --- USER ACTIONS
    public void loadDataWhenActivityStarts(Integer id){
        movieId = id;
        observeMovieTrailers(movieId);
        observeMovieReviews(movieId);

        getDetailMovie(false);
    }

    public void userClicksOnFavoriteButton(View view) {
        if (view.isSelected()) {
            favoriteState = false ;
        } else {
            favoriteState = true ;
        }
        view.setSelected(favoriteState);
        updateFavoriteMoviesUseCase.invoke(favoriteState, movieId);
    }

    public void userClicksOnReviewsButton(View view) {
        this.callback.get().openReviewModalBottomSheet();
    }

    public void userClicksOnVideosButton(View view) {
        this.callback.get().openVideoModalBottomSheet();
    }

    public void userClicksOnVideo(Video video) {
        String key = video.getKey();
        String url = YOUTUBE_BASE_URL+key;
        this.callback.get().openYouTube(url);
    }


    //  --- METHODS USED FOR USER ACTIONS
    private void getDetailMovie (Boolean forceRefresh) {
        _movie.removeSource(movieSource);
        movieSource = getDetailMovieUseCase.invoke(forceRefresh, movieId);
        _movie.addSource(movieSource , this::treatData);
    }

    // For Flowable, use ResourceSubscriber. For Observable, use DisposableSubscriber.
    // See this https://stackoverflow.com/questions/43169008/android-rxjava2-flowable-with-compositedisposable
    private void observeMovieTrailers(Integer movieId) {
        disposableTrailers.add(getReactiveMovieDetailUseCase.getMovieTrailers(movieId)
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribeWith(new ResourceSubscriber<List<Video>>() {
                                   @Override
                                   public void onNext(List<Video> videos) {
                                       _videos.setValue(videos);
                                       Log.d("DetailViewModel","On next Trailers "+videos.size());
                                   }

                                   @Override
                                   public void onError(Throwable t) {
                                       Log.d("DetailViewModel","An erroor happens");
                                   }

                                   @Override
                                   public void onComplete() {
                                       Log.d("DetailViewModel","Completed");
                                   }
                               }) );
    }

    private void observeMovieReviews(Integer movieId) {
        disposableReviews.add(getReactiveMovieDetailUseCase.getMovieReviews(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<List<Review>>() {
                    @Override
                    public void onNext(List<Review> reviews) {
                        _reviews.setValue(reviews);
                        Log.d("DetailViewModel","On next Reviews "+reviews.size());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("DetailViewModel","An erroor happens");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("DetailViewModel","Completed");
                    }
                }) );
    }

    private void treatData(Resource<Movie> resource) {
        this._movie.setValue(resource.getData());
        if (resource.getStatus() == Resource.Status.ERROR) _snackbarError.setValue(new Event<>(R.string.an_error_happened));
        if (resource.getStatus() == Resource.Status.NONETWORK) _snackbarError.setValue(new Event<>(R.string.no_network));
    }

    public void implementedListenerDetailViewModel (Listeners callback) { // call in onCreateView of DetailActivity, to initialise callback with its implementation present in HomeActivity
        Log.d("DetailViewModel", "Initializing implementedListener method");
        this.callback  = new WeakReference<> (callback);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.disposableTrailers.dispose();
        this.disposableReviews.dispose();
    }
}
