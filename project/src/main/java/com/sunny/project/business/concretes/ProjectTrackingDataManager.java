package com.sunny.project.business.concretes;

import com.sunny.project.business.abstracts.ProjectTrackingDataService;
import com.sunny.project.dataAccess.ProjectTrackingDataRepo;

import com.sunny.project.entities.ProjectTrackingData;

import com.sunny.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectTrackingDataManager implements ProjectTrackingDataService {
    @Autowired
    private ProjectTrackingDataRepo repository;

    @Override
    public List<ProjectTrackingData> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ProjectTrackingData> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ProjectTrackingData save(ProjectTrackingData projectTrackingData) {
        // Oranı kontrol et
        if (projectTrackingData.getRate() == null || projectTrackingData.getRate().getRate() == null) {
            throw new IllegalArgumentException("Oran değeri boş olamaz.");
        }

        Double rateValue;
        try {
            rateValue = Double.parseDouble(projectTrackingData.getRate().getRate().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Geçersiz oran değeri.");
        }

        Double totalRate = repository.findTotalRateByUserAndMonth(
                projectTrackingData.getUser().getId(), projectTrackingData.getMonth());

        if (totalRate == null) {
            totalRate = 0.0;
        }

        if (totalRate + rateValue > 1.0) {
            throw new IllegalArgumentException("Toplam oran 1.0'ı geçemez.");
        }


        ProjectTrackingData savedData = repository.save(projectTrackingData);

        updateProjectTotalRate(projectTrackingData.getProject().getId());

        return savedData;
    }


    @Override
    public void deleteById(Long id) {
        Optional<ProjectTrackingData> optionalProjectTrackingData = repository.findById(id);
        if (optionalProjectTrackingData.isPresent()) {
            ProjectTrackingData projectTrackingData = optionalProjectTrackingData.get();
            repository.deleteById(id);
            updateProjectTotalRate(projectTrackingData.getProject().getId());
        }
    }

    @Override
    public List<User> findUsersByProjectId(Long projectId) {
        List<ProjectTrackingData> dataList = repository.findByProjectId(projectId);
        return dataList.stream()
                .map(ProjectTrackingData::getUser) // Kullanıcıyı al
                .distinct() // Benzersiz kullanıcıları al
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalRateByProjectId(Long projectId) {
        Double totalRate = repository.findTotalRateByProject(projectId);
        if (totalRate == null) {
            totalRate = 0.0;
        }
        return totalRate;
    }

    private void updateProjectTotalRate(Long projectId) {
        Double totalRate = getTotalRateByProjectId(projectId);
        System.out.println("Project ID: " + projectId + " için güncellenen toplam oran: " + totalRate);
    }


    @Override
    public List<ProjectTrackingData> findByProjectIdAndUserId(Long projectId, Long userId) {
        return repository.findByProjectIdAndUserId(projectId, userId);
    }

    @Override
    public List<ProjectTrackingData> findByProjectId(Long projectId) {
        return repository.findByProjectId(projectId);
    }

}