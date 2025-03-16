package org.javakov.washappweb.controller;

import lombok.RequiredArgsConstructor;
import org.javakov.washappweb.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public CompletableFuture<String> addReview(@RequestParam String name, @RequestParam String text) {
        return reviewService.addReview(name, text);
    }

    @GetMapping
    public CompletableFuture<Map<String, Map<String, Object>>> getReviews() {
        return reviewService.getReviews();
    }
}
