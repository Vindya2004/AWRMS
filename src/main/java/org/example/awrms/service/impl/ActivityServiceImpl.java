package org.example.awrms.service.impl;

import org.example.awrms.dto.AccommodationDTO;
import org.example.awrms.dto.ActivityDTO;
import org.example.awrms.entity.Accommodation;
import org.example.awrms.entity.Activity;
import org.example.awrms.repository.ActivityRepository;
import org.example.awrms.service.ActivityService;
import org.example.awrms.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveActivity(ActivityDTO activityDTO) {
        if (activityRepository.existsByName(activityDTO.getName())) {
            return VarList.Not_Acceptable; // Accommodation already exists
        }
        Activity activity = modelMapper.map(activityDTO, Activity.class);
        activityRepository.save(activity);
        return VarList.Created; // Successfully saved
    }

    @Override
    public int updateActivity(Long id, ActivityDTO activityDTO) {
        Optional<Activity> existingActivityOpt = activityRepository.findById(id);
        if (existingActivityOpt.isPresent()) {
            Activity existingActivity = existingActivityOpt.get();

            existingActivity.setName(activityDTO.getName());
            existingActivity.setDescription(activityDTO.getDescription());
            existingActivity.setCostPerDay(activityDTO.getCostPerDay());

            if (activityDTO.getImageUrl() != null) {
                existingActivity.setImageUrl(activityDTO.getImageUrl());
            }

            activityRepository.save(existingActivity);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public int deleteActivity(Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return VarList.Created;
        }
        return VarList.Not_Found;
    }

    @Override
    public List<ActivityDTO> getAllActivity() {
        List<Activity> activities = activityRepository.findAll();
        return activities.stream()
                .map(activity -> modelMapper.map(activity, ActivityDTO.class))
                .collect(Collectors.toList());
    }
}
