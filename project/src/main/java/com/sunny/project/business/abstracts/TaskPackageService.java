package com.sunny.project.business.abstracts;

import com.sunny.project.entities.TaskPackage;
import com.sunny.project.entities.User;

import java.util.List;
import java.util.Optional;
public interface TaskPackageService {
    List<TaskPackage> getAllTaskPackages();
    Optional<TaskPackage> getTaskPackageById(Long id);
    TaskPackage createTaskPackage(TaskPackage taskPackage);
    TaskPackage updateTaskPackage(Long id, TaskPackage taskPackageDetails);
    void deleteTaskPackage(Long id);
    List<User> getUsersByIds(List<User> users);
    TaskPackage save(TaskPackage taskPackage);

    //27.07
    List<User> getUsersByTaskPackageId(Long taskPackageId);
}
