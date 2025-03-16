package org.javakov.washappweb.controller;

import com.google.firebase.database.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final DatabaseReference databaseRef;

    public ReviewController(DatabaseReference databaseRef) {
        this.databaseRef = databaseRef;
    }

    @PostMapping
    public CompletableFuture<String> addReview(@RequestParam String name, @RequestParam String text) {
        Map<String, Object> review = new HashMap<>();
        review.put("name", name);
        review.put("text", text);
        review.put("timestamp", System.currentTimeMillis());

        DatabaseReference newReviewRef = databaseRef.child("reviews").push();
        return CompletableFuture.supplyAsync(() -> {
            try {
                newReviewRef.setValueAsync(review).get();
                return "Review added successfully!";
            } catch (Exception e) {
                throw new RuntimeException("Failed to add review: " + e.getMessage());
            }
        });
    }

    @GetMapping
    public CompletableFuture<Map<String, Map<String, Object>>> getReviews() {
        CompletableFuture<Map<String, Map<String, Object>>> future = new CompletableFuture<>();

        databaseRef.child("reviews").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Map<String, Object>> reviews = new LinkedHashMap<>();
                List<DataSnapshot> snapshots = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshots.add(snapshot);
                }

                for (int i = snapshots.size() - 1; i >= 0; i--) {
                    DataSnapshot snapshot = snapshots.get(i);
                    reviews.put(snapshot.getKey(), snapshot.getValue(new GenericTypeIndicator<>() {}));
                }

                future.complete(reviews);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }
}
