package com.sunny.project.dataAccess;


import com.sunny.project.entities.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUserRepo extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail2);
}