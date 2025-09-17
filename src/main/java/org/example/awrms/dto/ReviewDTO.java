package org.example.awrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ReviewDTO {
    private String userEmail;
    private Long id;
    private String comment;
    private String createdAt;

    private int rating;
}
