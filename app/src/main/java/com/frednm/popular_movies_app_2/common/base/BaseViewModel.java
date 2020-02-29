package com.frednm.popular_movies_app_2.common.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.frednm.popular_movies_app_2.common.utils.Event;

public class BaseViewModel extends ViewModel {


    // ERROR HANDLER
    protected MutableLiveData<Event<Integer>> _snackbarError = new MutableLiveData<>();
    public LiveData<Event<Integer>> snackbarError = _snackbarError;

    public LiveData<Event<Integer>> getSnackbarError() {
        return snackbarError;
    }

}
