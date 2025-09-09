package org.example.awrms.service.impl;

import org.example.awrms.dto.DoctorDTO;
import org.example.awrms.entity.Doctor;
import org.example.awrms.repository.DoctorRepository;
import org.example.awrms.service.DoctorService;
import org.example.awrms.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

   @Autowired
   private ModelMapper modelMapper;
    @Override
    public int saveDoctor(DoctorDTO doctorDTO) {

        System.out.println(doctorRepository.existsByEmail(doctorDTO.getEmail()));

        if (doctorRepository.existsByEmail(doctorDTO.getEmail())) {
            return VarList.Not_Acceptable;
        }

        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

        doctorRepository.save(doctor);
        return VarList.Created;
    }

    @Override
    public int updateDoctor(String email, DoctorDTO doctorDTO) {
        Optional<Doctor> existingDoctorOpt = doctorRepository.findByEmail(email);
        if (existingDoctorOpt.isPresent()) {
            Doctor existingDoctor = existingDoctorOpt.get();

            existingDoctor.setFullName(doctorDTO.getFullName());
            existingDoctor.setDescription(doctorDTO.getDescription());
            existingDoctor.setLinkedin(doctorDTO.getLinkedin());
            existingDoctor.setPaymentPerDay(doctorDTO.getPaymentPerDay());

            if (doctorDTO.getImageUrl() != null) {
                existingDoctor.setImageUrl(doctorDTO.getImageUrl());
            }

            doctorRepository.save(existingDoctor);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public int deactivateDoctor(String email) {
        Optional<Doctor> existingDoctorOpt = doctorRepository.findByEmail(email);
        if (existingDoctorOpt.isPresent()) {
            Doctor existingDoctor = existingDoctorOpt.get();
            existingDoctor.setStatus("INACTIVE");
            doctorRepository.save(existingDoctor);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public int activateDoctor(String email) {
        Optional<Doctor> existingDoctorOpt = doctorRepository.findByEmail(email);
        if (existingDoctorOpt.isPresent()) {
            Doctor existingDoctor = existingDoctorOpt.get();
            existingDoctor.setStatus("ACTIVE");  // Change status to ACTIVE
            doctorRepository.save(existingDoctor);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> allDoctors = doctorRepository.findAll();
        return allDoctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> getAvailableDoctors() {
        List<Doctor> doctors = doctorRepository.findAllByBookedAndStatus("NO", "ACTIVE");
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public int deleteDoctor(String email) {

            Optional<Doctor> existing = doctorRepository.findByEmail(email);
            if (existing.isPresent()) {
                doctorRepository.delete(existing.get());
                return VarList.Created;
            }
            return VarList.Not_Found;
        }

}

