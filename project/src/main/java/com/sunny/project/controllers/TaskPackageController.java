package com.sunny.project.controllers;

import com.sunny.project.business.abstracts.TaskPackageService;
import com.sunny.project.entities.TaskPackage;

import com.sunny.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/taskPackages")
public class TaskPackageController {

    @Autowired
    private TaskPackageService taskPackageService;

    @GetMapping
    public List<TaskPackage> getAllTaskPackages() {
        return taskPackageService.getAllTaskPackages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskPackage> getTaskPackageById(@PathVariable Long id) {
        return taskPackageService.getTaskPackageById(id)
                .map(taskPackage -> ResponseEntity.ok(taskPackage))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/users")
    public ResponseEntity<List<User>> getUsersByTaskPackageId(@PathVariable Long id) {
        List<User> users = taskPackageService.getUsersByTaskPackageId(id);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TaskPackage> createTaskPackage(@RequestBody TaskPackage taskPackage) {

        TaskPackage createdTaskPackage = taskPackageService.createTaskPackage(taskPackage);
        return ResponseEntity.ok(createdTaskPackage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskPackage> updateTaskPackage(
            @PathVariable Long id,
            @RequestBody TaskPackage updatedTaskPackage) {
        TaskPackage taskPackage = taskPackageService.getTaskPackageById(id).orElse(null);
        if (taskPackage != null) {
            taskPackage.setTaskPackageName(updatedTaskPackage.getTaskPackageName());
            taskPackage.setUsers(updatedTaskPackage.getUsers());
            taskPackage.setStartDate(updatedTaskPackage.getStartDate());
            taskPackage.setEndDate(updatedTaskPackage.getEndDate());
            taskPackage.setUpdatedAt(updatedTaskPackage.getUpdatedAt());
            TaskPackage savedTaskPackage = taskPackageService.save(taskPackage);
            return ResponseEntity.ok(savedTaskPackage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskPackage(@PathVariable Long id) {
        taskPackageService.deleteTaskPackage(id);
        return ResponseEntity.noContent().build();
    }
}
