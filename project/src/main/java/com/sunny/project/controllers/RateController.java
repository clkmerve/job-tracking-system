package com.sunny.project.controllers;

import com.sunny.project.business.abstracts.RateService;
import com.sunny.project.entities.Rate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rates")
@AllArgsConstructor
public class RateController {

    @Autowired
    private RateService rateService;

    @GetMapping
    public List<Rate> getAllRates() {
        return rateService.getAll();
    }

    @PostMapping
    public Rate createRate(@RequestBody Rate newRate) {
        return rateService.save(newRate);
    }

    @PutMapping("/{id}")
    public Rate updateRate(@PathVariable Long id,@RequestBody Rate newRate){
        Optional<Rate> rate = rateService.findById(id);
        if (rate.isPresent()){
            Rate foundRate = rate.get();
            foundRate.setRate(newRate.getRate());
            foundRate.setHour(newRate.getHour());

            rateService.save(foundRate);
            return foundRate;
        }else return null;
    }

    @GetMapping("/{id}")
    public Optional<Rate> getRateById(@PathVariable Long id) {
        return rateService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRateById(@PathVariable Long id) {
        rateService.deleteById(id);
    }
}
