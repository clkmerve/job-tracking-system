package com.sunny.project.business.abstracts;

import com.sunny.project.entities.ProjectUser;

import java.util.List;
import java.util.Optional;

public interface ProjectUserService {
    List<ProjectUser> getAll();
    Optional<ProjectUser> getById(Long id);
    ProjectUser create(ProjectUser projectUser);
    Optional<ProjectUser> update(Long id, ProjectUser projectUser);
    void delete(Long id);
}
