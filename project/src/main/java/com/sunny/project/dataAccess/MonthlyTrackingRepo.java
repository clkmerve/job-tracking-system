package com.sunny.project.dataAccess;

import com.sunny.project.entities.MonthlyTracking;
import com.sunny.project.entities.TaskPackage;
import com.sunny.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MonthlyTrackingRepo extends JpaRepository<MonthlyTracking,Long> {
    List<MonthlyTracking> findByUserId(Long userId);
    List<MonthlyTracking> findByTaskPackageId(Long taskPackageId);
//    List<MonthlyTracking> findByUserAndDateBetween(User user, LocalDate date);

    List<MonthlyTracking> findByUserIdAndMonth(Long userId, String month);

    void deleteByTaskPackage(TaskPackage taskPackage);
}