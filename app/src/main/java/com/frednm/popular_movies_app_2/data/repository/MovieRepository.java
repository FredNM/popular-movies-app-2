package com.frednm.popular_movies_app_2.data.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.frednm.popular_movies_app_2.data.local.MovieDao;
import com.frednm.popular_movies_app_2.data.local.ReviewDao;
import com.frednm.popular_movies_app_2.data.local.VideoDao;
import com.frednm.popular_movies_app_2.data.model.ApiResult;
import com.frednm.popular_movies_app_2.data.model.Movie;
import com.frednm.popular_movies_app_2.data.model.Review;
import com.frednm.popular_movies_app_2.data.model.Video;
import com.frednm.popular_movies_app_2.data.remote.MovieDatasource;
import com.frednm.popular_movies_app_2.data.repository.utils.NetworkBoundResource;
import com.frednm.popular_movies_app_2.data.repository.utils.Resource;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import retrofit2.Call;

/**
 * Repository produces only LiveData.
 * After passing them to NetworkBoundResource, data produced are always wrapped in LiveData<Resource> thanks to asLiveData()
 */

@Singleton
public class MovieRepository {

    private MovieDatasource movieDatasource ;
    private MovieDao movieDao;
    private VideoDao videoDao  ;
    private ReviewDao reviewDao ;
    private Context context;

    Executor executor = Executors.newSingleThreadExecutor();

    protected static boolean isConnected;

    @Inject
    public MovieRepository(MovieDatasource movieDatasource, MovieDao movieDao, VideoDao videoDao, ReviewDao reviewDao, Context context) {
        this.movieDatasource = movieDatasource;
        this.movieDao = movieDao;
        this.videoDao = videoDao;
        this.reviewDao = reviewDao;
        this.context = context;
        this.handlingNetworkState();
    }

    // - HANDLING NETWORK CONNECTION
    // See this https://stackoverflow.com/questions/25678216/android-internet-connectivity-change-listener/58468010#58468010
    private void handlingNetworkState() {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                isConnected = true;
                Log.d("MovieRepository", "There is Network Connection");
            }

            @Override
            public void onLost(Network network) {
                isConnected = false;
                Log.d("MovieRepository", "No Network Connection");
            }
        };

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // For API > 24
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else { // For API > 21
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
    }

    /**
     * Get top rated movies only from DB. If shouldFetch returns true, then do the network request first, and save results in DB.
     * shouldFetch returns true if data is null or empty(empty DB), or when user swipes to refresh the screen.
     */
    public LiveData<Resource<List<Movie>>> getTopRatedMovies(Boolean forceRefresh){
        return new NetworkBoundResource<List<Movie>,ApiResult<Movie>>(){

            @Override
            protected List<Movie> processResponse(ApiResult<Movie> response) {
                List<Movie> list = response.getResults();
                for(int i=0;i<list.size();i++){
                    list.get(i).setLastRefreshed(new Date()) ;
                    list.get(i).setTopRated(true);
                }
                return list;
            }

            @Override
            protected void saveCallResults(@NonNull List<Movie> items) {
                movieDao.saveTopRatedMovies(items);
            }

            @Override
            protected Boolean shouldFetch(@Nullable List<Movie> data) {
                return data==null || data.isEmpty() || forceRefresh ;
            }

            @NonNull
            @Override
            protected List<Movie> loadFromDb() {
                return movieDao.getTopRatedMovies();
            }

            @NonNull
            @Override
            protected Call<ApiResult<Movie>> createCallAsync() {
                return movieDatasource.fetchTopRatedMovies();
            }
        }.asLiveData();
    }

    /**
     * Get popular movies only from DB. If shouldFetch returns true, then do the network request first, and save results in DB.
     * shouldFetch returns true if data is null or empty(empty DB), or when user swipes to refresh the screen.
     */
    public LiveData<Resource<List<Movie>>> getPopularMovies(Boolean forceRefresh){
        return new NetworkBoundResource<List<Movie>,ApiResult<Movie>>(){

            @Override
            protected List<Movie> processResponse(ApiResult<Movie> response) {
                List<Movie> list = response.getResults();
                for(int i=0;i<list.size();i++){
                    list.get(i).setLastRefreshed(new Date()) ;
                    list.get(i).setPopular(true);
                }
                return list;
            }

            @Override
            protected void saveCallResults(@NonNull List<Movie> items) {
                movieDao.savePopularMovies(items);
            }

            @Override
            protected Boolean shouldFetch(@Nullable List<Movie> data) {
                return data==null || data.isEmpty() || forceRefresh ;
            }

            @NonNull
            @Override
            protected List<Movie> loadFromDb() {
                return movieDao.getPopularMovies();
            }

            @NonNull
            @Override
            protected Call<ApiResult<Movie>> createCallAsync() {
                return movieDatasource.fetchPopularMovies();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Movie>>> getFavoriteMovies(){
        return new NetworkBoundResource<List<Movie>,ApiResult<Movie>>(){

            @Override
            protected List<Movie> processResponse(ApiResult<Movie> response) {
                return response.getResults(); // this is useless ! Just to return a list of movies,
                // as the fictive async network request returns a fictive response, wrapped in ApiResult<Movie>
            }

            @Override
            protected void saveCallResults(@NonNull List<Movie> items) { }

            @Override
            protected Boolean shouldFetch(@Nullable List<Movie> data) { return false ; }

            @NonNull
            @Override
            protected List<Movie> loadFromDb() {
                return movieDao.getFavoriteMovies();
            }

            @NonNull
            @Override
            protected Call<ApiResult<Movie>> createCallAsync() {
                return movieDatasource.fetchPopularMovies(); // useless ! Just to fill something. This will never be executed.
            }
        }.asLiveData();
    }

    /**
     * Get detail of a movie from DB. If shouldFetch returns true, then do the network request first, and save results in DB.
     * shouldFetch returns true if data is null (empty DB), or the runtime of the movie is missing (it's the only movie information
     * we don't have after fetching list of popular or top rated movies), or user swipes to refresh, or the time spent between
     * now and the previous network request is > 10 min (see haveToRefreshFromNetwork() method in Movie class).
     */
    public LiveData<Resource<Movie>> getDetailMovie(Boolean forceRefresh, Integer movieId){
        return new NetworkBoundResource<Movie,Movie>(){

            @Override
            protected Movie processResponse(Movie response) {
                if (response.getVideos().getResults() != null) {
                    for (int i = 0; i < response.getVideos().getResults().size(); i++) {
                        response.getVideos().getResults().get(i).setMovieId(movieId);
                    }
                }
                return response;
            }

            @Override
            protected void saveCallResults(@NonNull Movie item) {
                movieDao.updateMovie(item.getRuntime(),movieId); // Update Movie information in the DB, the runtime was missing
                videoDao.saveVideos(item.getVideos().getResults(), movieId); // Save videos (trailer's videos) of that Movie.
            }

            @Override
            protected Boolean shouldFetch(@Nullable Movie data) {
                return data==null || data.getRuntime()==null || forceRefresh || data.haveToRefreshFromNetwork() ;
            }

            @NonNull
            @Override
            protected Movie loadFromDb() {
                return movieDao.getMovie(movieId);
            }

            @NonNull
            @Override
            protected Call<Movie> createCallAsync() {
                return movieDatasource.fetchDetailMovie(movieId);
            }
        }.asLiveData();
    }

    /**
     * Get reviews of a movie. Reviews are detail of a Movie, so to be coherent, the previous method should be named
     * getVideosMovie and not getDetailMovie, because in the previous method the only details are the List of videos (trailers)
     * And in this method, the detail is the list of Reviews associated to a Movie
     */
    public LiveData<Resource<Movie>> getReviewsMovie(Boolean forceRefresh, Integer movieId){
        return new NetworkBoundResource<Movie,Movie>(){

            @Override
            protected Movie processResponse(Movie response) {
                if (response.getReviews().getResults() != null) {
                    for (int i = 0; i < response.getReviews().getResults().size(); i++) {
                        response.getReviews().getResults().get(i).setMovieId(movieId);
                    }
                }
                return response;
            }

            @Override
            protected void saveCallResults(@NonNull Movie item) {
                reviewDao.saveReviews(item.getReviews().getResults(), movieId);
            }

            @Override
            protected Boolean shouldFetch(@Nullable Movie data) {
                return data==null || data.getRuntime()==null || forceRefresh || data.haveToRefreshFromNetwork() ;
            }

            @NonNull
            @Override
            protected Movie loadFromDb() {
                return movieDao.getMovie(movieId);
            }

            @NonNull
            @Override
            protected Call<Movie> createCallAsync() {
                return movieDatasource.fetchReviewsMovie(movieId);
            }
        }.asLiveData();
    }

    // Oblige to do this task in background thread, otherwise Room will complain.
    // See https://medium.com/@tonia.tkachuk/android-app-example-using-room-database-63f7091e69af
    public void updateFavoriteMovies(Boolean isSelected, Integer movieId){
        executor.execute(() -> {
            movieDao.updateFavoriteMovies(isSelected, movieId);
        });
    }

    // --- REACTIVE METHODS

    public Flowable<List<Video>> getMovieTrailers(Integer movieId){
        return videoDao.getMovieTrailers(movieId, "Trailer").distinctUntilChanged();
    }

    public Flowable<List<Review>> getMovieReviews(Integer movieId){
        return reviewDao.getMovieReviews(movieId).distinctUntilChanged();
    }

}
