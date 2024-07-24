package com.sunny.project.business.concretes;

import com.sunny.project.business.abstracts.ProjectService;
import com.sunny.project.business.abstracts.ProjectUserService;
import com.sunny.project.business.abstracts.UserService;
import com.sunny.project.dataAccess.ProjectUserRepo;
import com.sunny.project.entities.Project;
import com.sunny.project.entities.ProjectUser;
import com.sunny.project.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ProjectUserManager implements ProjectUserService {

    @Autowired
    private ProjectUserRepo projectUserRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Override
    public List<ProjectUser> getAll() {
        return projectUserRepo.findAll();
    }

    @Override
    public Optional<ProjectUser> getById(Long id) {
        return projectUserRepo.findById(id);
    }
    @Override
    public ProjectUser create(ProjectUser projectUser) {
        return projectUserRepo.save(projectUser);
    }

    @Override
    public Optional<ProjectUser> update(Long id, ProjectUser projectUser) {
        if (projectUserRepo.existsById(id)) {
            projectUser.setId(id);
            return Optional.of(projectUserRepo.save(projectUser));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        projectUserRepo.deleteById(id);
    }


}
