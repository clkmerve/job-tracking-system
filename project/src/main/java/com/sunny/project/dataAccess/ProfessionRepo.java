package com.sunny.project.dataAccess;

import com.sunny.project.entities.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionRepo extends JpaRepository<Profession,Long> {
}
