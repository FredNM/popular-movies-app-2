package com.frednm.popular_movies_app_2.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewResult {

    @SerializedName("results")
    @Expose
    private List<Review> results ;

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

}
