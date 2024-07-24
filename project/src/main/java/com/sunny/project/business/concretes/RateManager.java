package com.sunny.project.business.concretes;

import com.sunny.project.business.abstracts.RateService;
import com.sunny.project.dataAccess.RateRepo;
import com.sunny.project.entities.Rate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RateManager implements RateService {

    @Autowired
    private RateRepo rateRepo;

    @Override
    public List<Rate> getAll() {
        return this.rateRepo.findAll();
    }

    @Override
    public Rate save(Rate newRate) {
        return rateRepo.save(newRate);
    }

    @Override
    public Optional<Rate> findById(Long id) {
        return rateRepo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        rateRepo.deleteById(id);
    }
}
