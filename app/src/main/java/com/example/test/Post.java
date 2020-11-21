package com.example.test;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {
    public float review_stars;
    public String review_text;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Post(float review_stars, String review_text) {
        this.review_stars = review_stars;
        this.review_text = review_text;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("review_stars", review_stars);
        result.put("review_text", review_text);

        return result;
    }
}
