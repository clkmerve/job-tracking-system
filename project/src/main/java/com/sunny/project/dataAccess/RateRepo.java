package com.sunny.project.dataAccess;

import com.sunny.project.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepo extends JpaRepository<Rate,Long> {
}
