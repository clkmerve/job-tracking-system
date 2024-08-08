package com.sunny.project.business.abstracts;

import com.sunny.project.entities.ProjectTrackingData;
import com.sunny.project.entities.User;

import java.util.List;
import java.util.Optional;

public interface ProjectTrackingDataService {
    List<ProjectTrackingData> findAll();
    Optional<ProjectTrackingData> findById(Long id);
    ProjectTrackingData save(ProjectTrackingData projectTrackingData);
    void deleteById(Long id);
    List<User> findUsersByProjectId(Long projectId);
    Double getTotalRateByProjectId(Long projectId);

    List<ProjectTrackingData> findByProjectIdAndUserId(Long projectId, Long userId);

    List<ProjectTrackingData> findByProjectId(Long projectId);

}
