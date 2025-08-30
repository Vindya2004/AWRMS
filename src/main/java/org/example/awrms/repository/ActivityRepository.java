package org.example.awrms.repository;

import org.example.awrms.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    boolean existsByName(String name);
}
