package org.example.awrms.repository;

import org.example.awrms.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {


    boolean existsByEmail(String email);

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findAllByBookedAndStatus(String no, String active);
}
