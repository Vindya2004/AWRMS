package org.example.awrms;

import org.example.awrms.dto.ActivityDTO;
import org.example.awrms.entity.Activity;
import org.example.awrms.repository.ActivityRepository;
import org.example.awrms.service.impl.ActivityServiceImpl;
import org.example.awrms.util.VarList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceImplTests {

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ModelMapper modelMapper;

    private ActivityDTO activityDTO;
    private Activity activity;

    @BeforeEach
    void setUp() {
        activityDTO = new ActivityDTO(1L, "Test Activity", "Test Description", "test.png", "100.00");
        activity = new Activity(1L, "Test Activity", "Test Description", "test.png", "100.00");
    }

    @Test
    void shouldSaveActivity() {
        // arrange
        when(activityRepository.existsByName(activityDTO.getName())).thenReturn(false);
        when(modelMapper.map(activityDTO, Activity.class)).thenReturn(activity);
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        // act
        int result = activityService.saveActivity(activityDTO);

        // assert
        assertEquals(VarList.Created, result);
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    void shouldNotSaveDuplicateActivity() {
        // arrange
        when(activityRepository.existsByName(activityDTO.getName())).thenReturn(true);

        // act
        int result = activityService.saveActivity(activityDTO);

        // assert
        assertEquals(VarList.Not_Acceptable, result);
        verify(activityRepository, never()).save(any(Activity.class));
    }

    @Test
    void shouldUpdateActivity() {
        // arrange
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        ActivityDTO updatedDTO = new ActivityDTO(1L, "Updated Activity", "Updated Description", "new.png", "150.00");

        // act
        int result = activityService.updateActivity(1L, updatedDTO);

        // assert
        assertEquals(VarList.Created, result);
        assertEquals("Updated Activity", activity.getName());
        assertEquals("Updated Description", activity.getDescription());
        assertEquals("new.png", activity.getImageUrl());
        assertEquals("150.00", activity.getCostPerDay());
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingActivity() {
        // arrange
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        // act
        int result = activityService.updateActivity(1L, activityDTO);

        // assert
        assertEquals(VarList.Not_Found, result);
        verify(activityRepository, never()).save(any(Activity.class));
    }

    @Test
    void shouldDeleteActivity() {
        // arrange
        when(activityRepository.existsById(1L)).thenReturn(true);

        // act
        int result = activityService.deleteActivity(1L);

        // assert
        assertEquals(VarList.Created, result);
        verify(activityRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingActivity() {
        // arrange
        when(activityRepository.existsById(1L)).thenReturn(false);

        // act
        int result = activityService.deleteActivity(1L);

        // assert
        assertEquals(VarList.Not_Found, result);
        verify(activityRepository, never()).deleteById(anyLong());
    }

    @Test
    void shouldReturnAllActivities() {
        // arrange
        Activity activity2 = new Activity(2L, "Another Activity", "Another Description", "another.png", "200.00");
        List<Activity> activities = List.of(activity, activity2);

        when(activityRepository.findAll()).thenReturn(activities);
        when(modelMapper.map(activity, ActivityDTO.class)).thenReturn(activityDTO);
        when(modelMapper.map(activity2, ActivityDTO.class))
                .thenReturn(new ActivityDTO(2L, "Another Activity", "Another Description", "another.png", "200.00"));

        // act
        List<ActivityDTO> result = activityService.getAllActivity();

        // assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(activityRepository, times(1)).findAll();
    }
}