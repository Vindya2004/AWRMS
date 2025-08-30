package org.example.awrms.service;

import org.example.awrms.dto.ActivityDTO;

import java.util.List;

public interface ActivityService {
    int saveActivity(ActivityDTO activityDTO);

    int updateActivity(Long id, ActivityDTO activityDTO);

    int deleteActivity(Long id);

    List<ActivityDTO> getAllActivity();
}
