package com.sunny.project.controllers;

import com.sunny.project.business.abstracts.ProjectService;
import com.sunny.project.business.abstracts.TaskPackageService;
import com.sunny.project.entities.Project;
import com.sunny.project.entities.TaskPackage;
import com.sunny.project.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor
public class ProjectController {
    private ProjectService projectService;
    @GetMapping
    public List<Project> getAll(){
        return this.projectService.getAll();
    }
    @PostMapping
    public Project createProject(@RequestBody Project newProject) {
        return projectService.save(newProject);
    }
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
        Optional<Project> project = projectService.getProjectById(projectId);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateOneProject(@PathVariable Long projectId, @RequestBody Project newProject) {
        newProject.setId(projectId);
        Project updatedProject = projectService.update(newProject);
        return ResponseEntity.ok(updatedProject);
    }
    @DeleteMapping("/{projectId}")
    public void deleteOneProject(@PathVariable Long projectId){
        projectService.deleteById(projectId);
    }

    @GetMapping("/{projectId}/users")
    public List<User> getUsersByProject(@PathVariable Long projectId) {
        return projectService.getUsersByProjectId(projectId);
    }

    private TaskPackageService taskPackageService;
    @PostMapping("/{projectId}/taskPackages")
    public ResponseEntity<TaskPackage> createTaskPackage(@PathVariable Long projectId, @RequestBody TaskPackage taskPackage) {
        Optional<Project> project = projectService.findById(projectId);
        if (project.isPresent()) {
            taskPackage.setProject(project.get()); // TaskPackage'e ilişkili Project nesnesini atama
            TaskPackage savedTaskPackage = taskPackageService.save(taskPackage);
            return ResponseEntity.ok(savedTaskPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{projectId}/taskPackages")
    public ResponseEntity<List<TaskPackage>> getTaskPackages(@PathVariable Long projectId) {
        Optional<Project> project = projectService.findById(projectId);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get().getTaskPackages());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
