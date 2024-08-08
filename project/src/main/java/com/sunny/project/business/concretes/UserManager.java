package com.sunny.project.business.concretes;

import com.sunny.project.business.abstracts.UserService;
import com.sunny.project.dataAccess.TaskPackageRepo;
import com.sunny.project.dataAccess.UserRepo;
import com.sunny.project.entities.Project;
import com.sunny.project.entities.TaskPackage;
import com.sunny.project.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class UserManager implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskPackageRepo taskPackageRepo;
    @Override
    public List<User> getAll() {
        return this.userRepo.findAll();
    }

    @Override
    public User save(User newUser) {
        return userRepo.save(newUser);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepo.findById(userId);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepo.findById(userId);
    }

    @Override
    public void deleteById(Long userId) {
        userRepo.deleteById(userId);
    }




    public List<Project> getUserAssignedProjects(User user) {
        List<TaskPackage> taskPackages = user.getTaskPackages(); // Kullanıcının aldığı tüm görev paketlerini al
        List<Project> projects = new ArrayList<>(); // Projeleri tutacak bir liste oluştur

        // Her görev paketinin projelerini al ve projeler listesine ekle
        for (TaskPackage taskPackage : taskPackages) {
            Project project = taskPackage.getProject(); // Görev paketinin ait olduğu proje
            if (project != null && !projects.contains(project)) { // Proje daha önce eklenmediyse
                projects.add(project); // Projeyi listeye ekle
            }
        }

        return projects; // Kullanıcının atandığı projelerin listesi
    }
    @Override
    public List<TaskPackage> getTaskPackagesByUserId(Long userId) {
        return taskPackageRepo.findByUsers_Id(userId);
    }
}

