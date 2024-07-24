package com.sunny.project.dataAccess;

import com.sunny.project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
