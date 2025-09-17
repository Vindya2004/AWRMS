package org.example.awrms.service;

import jakarta.validation.Valid;
import org.example.awrms.dto.ReviewDTO;

public interface ReviewService {
    int saveReview(@Valid ReviewDTO reviewDTO);

    Object getAllReviews();

    int deleteReview(Long id);
}
