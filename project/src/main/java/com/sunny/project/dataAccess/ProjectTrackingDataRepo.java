package com.sunny.project.dataAccess;
import com.sunny.project.entities.ProjectTrackingData;
import com.sunny.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectTrackingDataRepo extends JpaRepository<ProjectTrackingData, Long> {
    @Query("SELECT SUM(CAST(pd.rate.rate AS double)) FROM ProjectTrackingData pd WHERE pd.user.id = :userId AND pd.month = :month")
    Double findTotalRateByUserAndMonth(@Param("userId") Long userId, @Param("month") String month);

    List<ProjectTrackingData> findByProjectId(Long projectId);

    @Query("SELECT SUM(CAST(pd.rate.rate AS double)) FROM ProjectTrackingData pd WHERE pd.project.id = :projectId")
    Double findTotalRateByProject(@Param("projectId") Long projectId);

    @Query("SELECT pd FROM ProjectTrackingData pd WHERE pd.project.id = :projectId AND pd.user.id = :userId")
    List<ProjectTrackingData> findByProjectIdAndUserId(@Param("projectId") Long projectId, @Param("userId") Long userId);

}
