package com.sunny.project.controllers;

import com.sunny.project.business.abstracts.MonthlyTrackingService;
import com.sunny.project.entities.MonthlyTracking;
import com.sunny.project.entities.Rate;
import com.sunny.project.entities.TaskPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monthly-trackings")
public class MonthlyTrackingController {

    @Autowired
    private MonthlyTrackingService monthlyTrackingService;

    @GetMapping
    public ResponseEntity<List<MonthlyTracking>> getAllTrackings() {
        List<MonthlyTracking> trackings = monthlyTrackingService.getAllTrackings();

        return ResponseEntity.ok(trackings);
    }
    @GetMapping("/users/{userId}/taskPackages")
    public ResponseEntity<List<TaskPackage>> getTaskPackagesForUser(@PathVariable Long userId) {
        List<TaskPackage> taskPackages = monthlyTrackingService.getTaskPackagesForUser(userId);
        return ResponseEntity.ok(taskPackages);
    }
    @GetMapping("/rates")
    public ResponseEntity<List<Rate>> getRates() {
        List<Rate> rates = monthlyTrackingService.getRates();
        return ResponseEntity.ok(rates);
    }
    @PostMapping
    public ResponseEntity<MonthlyTracking> createTracking(@RequestBody MonthlyTracking tracking) {
        MonthlyTracking savedTracking = monthlyTrackingService.saveTracking(tracking);
        return ResponseEntity.ok(savedTracking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyTracking> updateTracking(@PathVariable Long id, @RequestBody MonthlyTracking tracking) {
        MonthlyTracking updatedTracking = monthlyTrackingService.updateTracking(id, tracking);
        return ResponseEntity.ok(updatedTracking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTracking(@PathVariable Long id) {
        monthlyTrackingService.deleteTracking(id);
        return ResponseEntity.noContent().build();
    }

}