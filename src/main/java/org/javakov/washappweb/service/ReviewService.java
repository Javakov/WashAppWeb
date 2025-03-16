package org.javakov.washappweb.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ReviewService {

    CompletableFuture<String> addReview(String name, String text);

    CompletableFuture<Map<String, Map<String, Object>>> getReviews();
}
