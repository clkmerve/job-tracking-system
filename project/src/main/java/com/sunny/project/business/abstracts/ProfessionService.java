package com.sunny.project.business.abstracts;

import com.sunny.project.entities.Profession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProfessionService {
    //metotları tanımladığım yer.
    List<Profession> getAll();
    Profession save(Profession newProfession);
    Optional<Profession> getProfessionById(Long professionId);
    Optional<Profession> findById(Long professionId);
    void deleteById(Long professionId);
}
