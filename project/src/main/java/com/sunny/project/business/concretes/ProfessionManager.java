package com.sunny.project.business.concretes;

import com.sunny.project.business.abstracts.ProfessionService;
import com.sunny.project.dataAccess.ProfessionRepo;
import com.sunny.project.entities.Profession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessionManager implements ProfessionService {
    @Autowired
    private ProfessionRepo professionRepo;
    @Override
    public List<Profession> getAll() {
        return this.professionRepo.findAll();
    }

    @Override
    public Profession save(Profession newProfession) {
        return professionRepo.save(newProfession);
    }
    @Override
    public Optional<Profession> getProfessionById(Long professionId) {
        return professionRepo.findById(professionId);
    }
    @Override
    public Optional<Profession> findById(Long professionId) {
        return professionRepo.findById(professionId);
    }

    @Override
    public void deleteById(Long professionId) {
        professionRepo.deleteById(professionId);
    }
}
