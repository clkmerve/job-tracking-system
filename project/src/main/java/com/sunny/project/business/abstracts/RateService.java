package com.sunny.project.business.abstracts;

import com.sunny.project.entities.Rate;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public interface RateService {
    List<Rate> getAll();
    Rate save(Rate newRate);
    Optional<Rate> findById(Long id);
    void deleteById(Long id);
}
