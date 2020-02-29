package com.frednm.popular_movies_app_2.features.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.frednm.popular_movies_app_2.R;
import com.frednm.popular_movies_app_2.common.base.BaseViewModel;
import com.frednm.popular_movies_app_2.common.utils.Event;
import com.frednm.popular_movies_app_2.data.model.Movie;
import com.frednm.popular_movies_app_2.data.repository.utils.Resource;
import com.frednm.popular_movies_app_2.features.home.domain.GetFavoriteMoviesUseCase;
import com.frednm.popular_movies_app_2.features.home.domain.GetPopularMoviesUseCase;
import com.frednm.popular_movies_app_2.features.home.domain.GetTopRatedMoviesUseCase;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;


public class HomeViewModel extends BaseViewModel {

    public interface Listeners {
        void navigateToDetailActivity(Integer movieId);
    }
    private WeakReference<Listeners> callback;

    private GetPopularMoviesUseCase getPopularMoviesUseCase;
    private GetTopRatedMoviesUseCase getTopRatedMoviesUseCase;
    private GetFavoriteMoviesUseCase getFavoriteMoviesUseCase;

    // FOR DATA SHOWN ON UI
    private MediatorLiveData<Resource<List<Movie>>> _movies = new MediatorLiveData<>();
    public LiveData<Resource<List<Movie>>> movies = _movies;
    private LiveData<Resource<List<Movie>>> moviesSource = new MutableLiveData<>();

    // SOME SETTING PARAMETERS
    private String sortingCriteria;

    private static final String popular = "popular";
    private static final String topRated = "topRated";
    private static final String favorite = "favorite";


    // --- CONSTRUCTOR
    @Inject
    public HomeViewModel(GetPopularMoviesUseCase getPopularMoviesUseCase, GetTopRatedMoviesUseCase getTopRatedMoviesUseCase, GetFavoriteMoviesUseCase getFavoriteMoviesUseCase) {
        this.getPopularMoviesUseCase = getPopularMoviesUseCase;
        this.getTopRatedMoviesUseCase = getTopRatedMoviesUseCase;
        this.getFavoriteMoviesUseCase = getFavoriteMoviesUseCase;
    }

    //  --- USER ACTIONS
    public void userClicksOnItem(Movie movie) {
        Log.d("HomeViewModel", "Entering in userClicksOnItem method");
        this.callback.get().navigateToDetailActivity(movie.getId());
    }

    public void userRefreshesItems() {
        getMovies(true);
    }

    public void userChangesSettings(String sortingCriteria) {
        this.sortingCriteria = sortingCriteria;
        getMovies(false);
    }

    //  --- METHODS USED FOR USER ACTIONS
    private void getMovies (Boolean forceRefresh) {
        _movies.removeSource(moviesSource);
        if (this.sortingCriteria.equals(popular)) {
            moviesSource = getPopularMoviesUseCase.invoke(forceRefresh);
        } else if (this.sortingCriteria.equals(topRated)) {
            moviesSource = getTopRatedMoviesUseCase.invoke(forceRefresh);
        } else if (this.sortingCriteria.equals(favorite)) {
            moviesSource = getFavoriteMoviesUseCase.invoke();
        } else {
            moviesSource = null;
        }
        _movies.addSource(moviesSource, this::treatData);
    }

    private void treatData(Resource<List<Movie>> resource) {
        this._movies.setValue(resource);
        if (resource.getStatus() == Resource.Status.ERROR) _snackbarError.setValue(new Event<>(R.string.an_error_happened));
    }

    public void implementedListener(Listeners callback) { // call in onCreate of HomeActivity, to initialise callback with its implementation present in HomeActivity
        Log.d("HomeViewModel", "Initializing implementedListener method");
        this.callback  = new WeakReference<> (callback);
    }

}
