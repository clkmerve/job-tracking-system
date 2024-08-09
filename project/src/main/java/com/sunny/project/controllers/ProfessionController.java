package com.sunny.project.controllers;


import com.sunny.project.business.abstracts.ProfessionService;
import com.sunny.project.entities.Profession;
import com.sunny.project.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professions")
@AllArgsConstructor
public class ProfessionController {

    private ProfessionService professionService;
    @GetMapping
    public List<Profession> getAll() {
        return this.professionService.getAll();
    }
    @PostMapping
    public Profession createProfession(@RequestBody Profession newProfession){
        return professionService.save(newProfession);
    }
    @GetMapping("/{professionId}")
    public Profession getProfessionById(@PathVariable Long professionId) {

        return professionService.getProfessionById(professionId).orElse(null);
    }
    @PutMapping("/{professionId}")
    public Profession updateProfession(@PathVariable Long professionId,@RequestBody Profession newProfession){
        Optional<Profession> profession = professionService.findById(professionId);
        if (profession.isPresent()){
            Profession foundProfession = profession.get();
            foundProfession.setProfession(newProfession.getProfession());
            foundProfession.setDescription(newProfession.getDescription());
            foundProfession.setDate(newProfession.getDate());
            professionService.save(foundProfession);
            return foundProfession;
        }else return null;
    }
    @DeleteMapping("/{professionId}")
    public void deleteOneProfession(@PathVariable Long professionId){
        professionService.deleteById(professionId);
    }

}
