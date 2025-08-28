package org.example.awrms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class AccommodationDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String location;
    private String category;
    private String costPerDay;
}
