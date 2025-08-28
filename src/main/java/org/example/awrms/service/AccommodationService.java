package org.example.awrms.service;

import org.example.awrms.dto.AccommodationDTO;

import java.util.List;

public interface AccommodationService {
    int saveAccommodation(AccommodationDTO accommodationDTO);

    int updateAccommodation(Long id, AccommodationDTO accommodationDTO);

    int deleteAccommodation(Long id);

    List<AccommodationDTO> getAllAccommodation();
}
