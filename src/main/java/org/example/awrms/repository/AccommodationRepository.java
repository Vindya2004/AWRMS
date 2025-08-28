package org.example.awrms.repository;

import org.example.awrms.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    boolean existsByName(String name);
    Optional<Accommodation> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

}
