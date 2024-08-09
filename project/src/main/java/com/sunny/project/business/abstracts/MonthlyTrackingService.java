package com.sunny.project.business.abstracts;

import com.sunny.project.entities.MonthlyTracking;
import com.sunny.project.entities.Rate;
import com.sunny.project.entities.TaskPackage;

import java.util.List;
import java.util.Optional;

public interface MonthlyTrackingService {
    List<MonthlyTracking> getAllTrackings();
    MonthlyTracking saveTracking(MonthlyTracking tracking);
    MonthlyTracking updateTracking(Long id, MonthlyTracking tracking);
    void deleteTracking(Long id);
    List<TaskPackage> getTaskPackagesForUser(Long userId);

    List<Rate> getRates();
    List<MonthlyTracking> findByUserIdAndMonth(Long userId, String month);


}
