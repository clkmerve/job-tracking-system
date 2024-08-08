package com.sunny.project.business.concretes;

import com.sunny.project.business.abstracts.ProjectUserService;
import com.sunny.project.business.abstracts.TaskPackageService;
import com.sunny.project.business.abstracts.UserService;
import com.sunny.project.dataAccess.MonthlyTrackingRepo;
import com.sunny.project.dataAccess.TaskPackageRepo;
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

@AllArgsConstructor
@Service
public class TaskPackageManager implements TaskPackageService {

    @Autowired
    private TaskPackageRepo taskPackageRepository;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private UserService userService;

    @Autowired
    private MonthlyTrackingRepo monthlyTrackingRepo;
    @Override
    public List<TaskPackage> getAllTaskPackages() {
        return taskPackageRepository.findAll();
    }
    @Override
    public Optional<TaskPackage> getTaskPackageById(Long id) {
        return taskPackageRepository.findById(id);
    }
    @Override
    public TaskPackage createTaskPackage(TaskPackage taskPackage) {
        ProjectUser projectUser = new ProjectUser();
        List<User> users = taskPackage.getUsers();

        for (User user : users) {
            projectUser.setUser(user);
            projectUser.setProject(taskPackage.getProject());
            this.projectUserService.create(projectUser);
        }
        return taskPackageRepository.save(taskPackage);}

    @Override
    public TaskPackage updateTaskPackage(Long id, TaskPackage taskPackageDetails) {
        Optional<TaskPackage> taskPackage = taskPackageRepository.findById(id);
        if (taskPackage.isPresent()) {
            TaskPackage existingTaskPackage = taskPackage.get();
            existingTaskPackage.setTaskPackageName(taskPackageDetails.getTaskPackageName());
            existingTaskPackage.setUsers(taskPackageDetails.getUsers());
            existingTaskPackage.setStartDate(taskPackageDetails.getStartDate()); // Başlangıç tarihini güncelle
            existingTaskPackage.setEndDate(taskPackageDetails.getEndDate()); // Bitiş tarihini güncelle
            existingTaskPackage.setUpdatedAt(taskPackageDetails.getUpdatedAt()); // Güncelleme tarihini güncelle

            return taskPackageRepository.save(existingTaskPackage);
        } else {
            return null;
        }
    }

@Transactional
@Override
public void deleteTaskPackage(Long id) {
    // İlgili iş paketini bul
    Optional<TaskPackage> taskPackageOptional = taskPackageRepository.findById(id);
    if (taskPackageOptional.isPresent()) {
        TaskPackage taskPackage = taskPackageOptional.get();

        // İlgili MonthlyTracking kayıtlarını sil
        monthlyTrackingRepo.deleteByTaskPackage(taskPackage);

        // İş paketini sil
        taskPackageRepository.deleteById(id);
    }
}

    public List<User> getUsersByIds(List<User> userIds) {
        List<User> userList = new ArrayList<>();
        for (User userId : userIds) {
            Optional<User> userOptional = userService.getUserById(userId.getId());
            userOptional.ifPresent(userList::add);
        }
        return userList;
    }

    @Override
    public TaskPackage save(TaskPackage taskPackage) {
        return taskPackageRepository.save(taskPackage);
    }


    @Override
    public List<User> getUsersByTaskPackageId(Long taskPackageId) {
        TaskPackage taskPackage = taskPackageRepository.findById(taskPackageId).orElse(null);
        if (taskPackage != null) {
            return taskPackage.getUsers();
        }
        return List.of();
    }
}
