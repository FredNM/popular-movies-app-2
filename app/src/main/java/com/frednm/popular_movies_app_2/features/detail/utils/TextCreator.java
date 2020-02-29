package com.frednm.popular_movies_app_2.features.detail.utils;

import android.util.Log;

import com.frednm.popular_movies_app_2.data.model.Review;
import com.frednm.popular_movies_app_2.data.model.Video;

import java.util.List;

/**
 * Instead of creating Custom Data Binding on TextView, use the usual android:text, and call methods of this class to do
 * the text modifications needed.
 * See https://medium.com/androiddevelopers/data-binding-lessons-learnt-4fd16576b719
 */

public class TextCreator {

    // Take the date and return the year. For example, take 2019-5-30, and return 2019
    public String extractYear(String date) {
        if (date != null && !date.isEmpty()) {
            return date.substring(0, 4);
        } else {
            return null;
        }
    }

    // Take duration and return it with "min" at the end. For example, take 120, and return 120 min
    public String durationInMin (Integer duration) {
        return duration+" min";
    }

    // Take vote average, and return it with /10. For example, take 8.1, and return 8.1/10
    public String voteAverage(Double vote) {
        return vote+"/10";
    }

    // Take list of videos, and return xx TRAILERS. For example, if list of videos contains 3 videos, returns 3 TRAILERS
    public String numberTrailers(List<Video> videos) {
        if (videos != null ) {
            switch (videos.size()) {
                case 0:
                    return "NO TRAILER";
                case 1:
                    return videos.size() +" TRAILER";
                default:
                    return videos.size() + " TRAILERS"; // S'il ne vaut ni 0 ni 1, il vaut plus d'un !
            }
        } else {
            return "NO TRAILER";
        }
    }

    // Take list of reviews, and return xx COMMENTS. For example, if list of reviews contains 3 reviews, returns 3 COMMENTS
    public String numberReviews(List<Review> reviews) {
        if (reviews != null) {
            switch (reviews.size()) {
                case 0:
                    return "NO COMMENT";
                case 1:
                    return reviews.size() +" COMMENT";
                default:
                    Log.d("TextCreator","Number of reviews "+reviews.size());
                    return reviews.size() + " COMMENTS"; // S'il ne vaut ni 0 ni 1, il vaut plus d'un !
            }
        } else {
            return "NO COMMENT";
        }
    }

}
