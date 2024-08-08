package com.sunny.project.business.concretes;

import com.sunny.project.business.abstracts.ProjectService;
import com.sunny.project.business.abstracts.TaskPackageService;
import com.sunny.project.dataAccess.ProjectRepo;
import com.sunny.project.dataAccess.ProjectUserRepo;
import com.sunny.project.dataAccess.UserRepo;
import com.sunny.project.entities.Project;
import com.sunny.project.entities.ProjectUser;
import com.sunny.project.entities.TaskPackage;
import com.sunny.project.entities.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectManager implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private ProjectUserRepo projectUserRepository;

    @Autowired
    private UserRepo userRepository;

    @Transactional
    @Override
    public List<Project> getAll() {
        return this.projectRepo.findAll();
    }

    @Transactional
    @Override
    public Project save(Project newProject) {
        return projectRepo.save(newProject);
    }

    @Transactional
    @Override
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepo.findById(projectId);
    }

    @Transactional
    @Override
    public Optional<Project> findById(Long projectId) {
        return projectRepo.findById(projectId);
    }

    @Transactional
    @Override
    public void deleteById(Long projectId) {
        projectRepo.deleteById(projectId);

    }

    @Transactional
    @Override
    public Project update(Project project) {
        Optional<Project> existingProjectOptional = projectRepo.findById(project.getId());
        // veritabanında verilen ID'ye sahip bir proje arar ve sonuç olarak bir Optional<Project> döner.
        if (existingProjectOptional.isPresent()) {
            Project existingProject = existingProjectOptional.get();
            existingProject.setProjectName(project.getProjectName());
            existingProject.setProjectCode(project.getProjectCode());
            existingProject.setStartDate(project.getStartDate());
            existingProject.setEndDate(project.getEndDate());
            existingProject.setDescription(project.getDescription());
            existingProject.setCreationDate(project.getCreationDate());
            existingProject.setIsActive(project.getIsActive());
            existingProject.setCategory(project.getCategory());

            // Update task packages
            if (existingProject.getTaskPackages() == null) {
                existingProject.setTaskPackages(new ArrayList<>());
            } else {
                existingProject.getTaskPackages().clear();
            }
            if (project.getTaskPackages() != null) {
                for (TaskPackage taskPackage : project.getTaskPackages()) {
                    taskPackage.setProject(existingProject);
                    existingProject.getTaskPackages().add(taskPackage);
                }
            }

            // Update project users
            if (existingProject.getProjectUsers() == null) {
                existingProject.setProjectUsers(new ArrayList<>());
            } else {
                existingProject.getProjectUsers().clear();
            }
            if (project.getProjectUsers() != null) {
                for (ProjectUser projectUser : project.getProjectUsers()) {
                    projectUser.setProject(existingProject);
                    existingProject.getProjectUsers().add(projectUser);
                }
            }


            return projectRepo.save(existingProject);
        } else {
            throw new RuntimeException("Project not found.");
        }
    }

    @Transactional
    public List<User> getUsersByProjectId(Long projectId) {
        return projectUserRepository.findByProjectId(projectId).stream()
                .map(ProjectUser::getUser)
                .collect(Collectors.toList());
    }
}
