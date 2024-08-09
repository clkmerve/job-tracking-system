package com.sunny.project.business.abstracts;
import com.sunny.project.entities.TaskPackage;
import com.sunny.project.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    User save(User newUser);

    Optional<User> getUserById(Long userId);

    Optional<User> findById(Long userId);

    void deleteById(Long userId);

    List<TaskPackage> getTaskPackagesByUserId(Long userId);
}
