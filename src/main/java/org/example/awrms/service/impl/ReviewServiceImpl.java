package org.example.awrms.service.impl;

import org.example.awrms.dto.ReviewDTO;
import org.example.awrms.entity.Review;
import org.example.awrms.repository.ReviewRepository;
import org.example.awrms.repository.UserRepository;
import org.example.awrms.service.ReviewService;
import org.example.awrms.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public int saveReview(ReviewDTO reviewDTO) {
        try {
            // 1. Check user exists
            boolean userExists = userRepository.existsByEmail(reviewDTO.getUserEmail());
            if (!userExists) {
                return VarList.Not_Acceptable; // Invalid user
            }

            // 2. Validate rating (1-5)
            if (reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
                return VarList.Not_Acceptable; // Invalid rating
            }

            // 3. Map DTO -> Entity
            Review review = modelMapper.map(reviewDTO, Review.class);

            // 4. Set createdAt timestamp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            review.setCreatedAt(LocalDateTime.now().format(formatter));

            // 5. Save review
            reviewRepository.save(review);

            return VarList.Created;

        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }

    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int deleteReview(Long id) {
        try {
            if (reviewRepository.existsById(id)) {
                reviewRepository.deleteById(id);
                return VarList.Created;
            } else {
                return VarList.Not_Found;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;
        }
    }
}
