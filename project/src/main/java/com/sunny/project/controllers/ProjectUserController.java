package com.sunny.project.controllers;

import com.sunny.project.business.abstracts.ProjectUserService;
import com.sunny.project.entities.ProjectUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-users")
@AllArgsConstructor
public class ProjectUserController {

    private final ProjectUserService projectUserService;

    @GetMapping
    public List<ProjectUser> getAll() {
        return this.projectUserService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectUser> getById(@PathVariable Long id) {
        return projectUserService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProjectUser> create(@RequestBody ProjectUser projectUser) {
        ProjectUser createdProjectUser = projectUserService.create(projectUser);
        return ResponseEntity.ok(createdProjectUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectUser> update(@PathVariable Long id, @RequestBody ProjectUser projectUser) {
        return projectUserService.update(id, projectUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (projectUserService.getById(id).isPresent()) {
            projectUserService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
