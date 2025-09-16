package org.example.awrms.repository;

import org.example.awrms.entity.TreatPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreatPackageRepository extends JpaRepository<TreatPackage, Long> {
    boolean existsByName(String name);

    Optional<TreatPackage> findByName(String packageName);
}
