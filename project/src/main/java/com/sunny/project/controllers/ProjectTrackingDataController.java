package com.sunny.project.controllers;

import com.sunny.project.business.abstracts.ProjectTrackingDataService;
import com.sunny.project.entities.ProjectTrackingData;
import com.sunny.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project-tracking-data")
public class ProjectTrackingDataController {

    @Autowired
    private ProjectTrackingDataService service;

    @GetMapping
    public ResponseEntity<List<ProjectTrackingData>> getAllProjectTrackingData() {
        List<ProjectTrackingData> data = service.findAll();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectTrackingData> getProjectTrackingDataById(@PathVariable Long id) {
        Optional<ProjectTrackingData> data = service.findById(id);
        return data.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProjectTrackingData(@RequestBody ProjectTrackingData projectTrackingData) {
        try {
            ProjectTrackingData savedData = service.save(projectTrackingData);
            return ResponseEntity.ok(savedData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectTrackingData> updateProjectTrackingData(@PathVariable Long id, @RequestBody ProjectTrackingData projectTrackingData) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        projectTrackingData.setId(id);
        ProjectTrackingData updatedData = service.save(projectTrackingData);
        return ResponseEntity.ok(updatedData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectTrackingData(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{projectId}")
    public ResponseEntity<List<User>> getUsersByProjectId(@PathVariable Long projectId) {
        List<User> users = service.findUsersByProjectId(projectId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/project/{projectId}/total-rate")
    public ResponseEntity<Double> getTotalRateByProject(@PathVariable Long projectId) {
        Double totalRate = service.getTotalRateByProjectId(projectId);
        return ResponseEntity.ok(totalRate);
    }

    @GetMapping("/user-data/{projectId}/{userId}")
    public ResponseEntity<List<ProjectTrackingData>> getUserDataByProjectIdAndUserId(@PathVariable Long projectId, @PathVariable Long userId) {
        List<ProjectTrackingData> data = service.findByProjectIdAndUserId(projectId, userId);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/projectId/{projectId}")
    public ResponseEntity<List<ProjectTrackingData>> getProjectTrackingDataByProjectId(@PathVariable Long projectId) {
        List<ProjectTrackingData> data = service.findByProjectId(projectId);
        return ResponseEntity.ok(data);
    }
}