package org.example.awrms.service.impl;

import org.example.awrms.dto.TreatPackageDTO;
import org.example.awrms.entity.Accommodation;
import org.example.awrms.entity.Activity;
import org.example.awrms.entity.TreatPackage;
import org.example.awrms.repository.AccommodationRepository;
import org.example.awrms.repository.ActivityRepository;
import org.example.awrms.repository.TreatPackageRepository;
import org.example.awrms.service.TreatPackageService;
import org.example.awrms.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TreatPackageServiceImpl implements TreatPackageService {
    @Autowired
    private TreatPackageRepository treatPackageRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public int savePackage(TreatPackageDTO treatPackageDTO) {
        if (treatPackageRepository.existsByName(treatPackageDTO.getName())) {
            return VarList.Not_Acceptable;
        }

        TreatPackage treatPackage = modelMapper.map(treatPackageDTO, TreatPackage.class);

        List<Activity> activityList = treatPackageDTO.getActivities().stream()
                .map(idStr -> activityRepository.findById(Long.parseLong(String.valueOf(idStr)))
                        .orElseThrow(() -> new RuntimeException("Activity not found: " + idStr)))
                .collect(Collectors.toList());

        treatPackage.setActivities(activityList);
        treatPackageRepository.save(treatPackage);
        return VarList.Created;
    }

    @Override
    public List<TreatPackageDTO> getAllPackages() {
        List<TreatPackage> treatPackages = treatPackageRepository.findAll();

        return treatPackages.stream().map(treatPackage -> {
            TreatPackageDTO treatPackageDTO = new TreatPackageDTO();
            treatPackageDTO.setId(treatPackage.getId());
            treatPackageDTO.setName(treatPackage.getName());
            treatPackageDTO.setPrice(treatPackage.getPrice());
            treatPackageDTO.setEstimateDays(treatPackage.getEstimateDays());
            treatPackageDTO.setImageUrl(treatPackage.getImageUrl());

            if (treatPackage.getActivities() != null) {
                List<String> activityNames = treatPackage.getActivities()
                        .stream()
                        .map(Activity::getName)
                        .collect(Collectors.toList());
                treatPackageDTO.setActivities(activityNames);
            }
            return treatPackageDTO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int updatePackage(Long id, TreatPackageDTO treatPackageDTO) {
        Optional<TreatPackage> treatPackageOpt = treatPackageRepository.findById(id);
        if (treatPackageOpt.isPresent()) {
            TreatPackage treatPackage = modelMapper.map(treatPackageDTO, TreatPackage.class);
            treatPackage.setId(id);
            treatPackage.setActivities(null);

            if (treatPackageDTO.getActivities() != null && !treatPackageDTO.getActivities().isEmpty()) {
                List<Activity> activityList = treatPackageDTO.getActivities().stream()
                        .map(accId -> activityRepository.findById(Long.parseLong(String.valueOf(accId)))
                                .orElseThrow(() -> new RuntimeException("Activity not found: " + accId)))
                        .collect(Collectors.toList());
                treatPackage.setActivities(activityList);
            }

            treatPackageRepository.save(treatPackage);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    @Transactional
    public int deletePackage(Long id) {
        if (treatPackageRepository.existsById(id)) {
            treatPackageRepository.deleteById(id);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }
}