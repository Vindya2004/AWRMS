package org.example.awrms.service;

import org.example.awrms.dto.DoctorDTO;
import org.example.awrms.entity.Doctor;

import java.util.List;

public interface DoctorService {


    int saveDoctor(DoctorDTO doctorDTO);

    int updateDoctor(String email, DoctorDTO doctorDTO);

    int deactivateDoctor(String email);

    int activateDoctor(String email);

    List<DoctorDTO> getAllDoctors();

    List<DoctorDTO> getAvailableDoctors();

    int deleteDoctor(String email);
}
