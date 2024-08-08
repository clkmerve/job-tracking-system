package com.sunny.project.business.abstracts;

import com.sunny.project.entities.Project;
import com.sunny.project.entities.User;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> getAll();

    Project save(Project newProject);

    Optional<Project> getProjectById(Long projectId);

    Optional<Project> findById(Long projectId);

    void deleteById(Long projectId);

    Project update(Project project);

    List<User> getUsersByProjectId(Long projectId);
}
