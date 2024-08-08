package com.sunny.project.business.concretes;

import com.sunny.project.business.abstracts.MonthlyTrackingService;
import com.sunny.project.dataAccess.MonthlyTrackingRepo;
import com.sunny.project.dataAccess.RateRepo;
import com.sunny.project.entities.MonthlyTracking;
import com.sunny.project.entities.Rate;
import com.sunny.project.entities.TaskPackage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MonthlyTrackingManager implements MonthlyTrackingService {

    @Autowired
    private MonthlyTrackingRepo monthlyTrackingRepo;

    @Autowired
    private RateRepo rateRepo;

    @Override
    public List<MonthlyTracking> getAllTrackings() {
        return monthlyTrackingRepo.findAll();
    }

    @Override
    public MonthlyTracking updateTracking(Long id, MonthlyTracking tracking) {
        MonthlyTracking existingTracking = monthlyTrackingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Aylık izleme bulunamadı: " + id));


        List<MonthlyTracking> existingTrackings = monthlyTrackingRepo.findByUserIdAndMonth(tracking.getUser().getId(), tracking.getMonth())
                .stream()
                .filter(mt -> !mt.getId().equals(id))
                .collect(Collectors.toList());

        double currentTotalAllocation = existingTrackings.stream()
                .mapToDouble(mt -> {
                    if (mt.getRate() != null && mt.getRate().getRate() != null) {
                        try {
                            return Double.parseDouble(mt.getRate().getRate());
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Rate değeri geçerli bir sayı değil: " + mt.getRate().getRate());
                        }
                    } else {
                        return 0.0; // Rate null ise 0.0 döndür
                    }
                })
                .sum();


        if (tracking.getRate() != null && tracking.getRate().getRate() != null) {
            try {
                currentTotalAllocation += Double.parseDouble(tracking.getRate().getRate());
            } catch (NumberFormatException e) {
                throw new RuntimeException("Yeni atama için rate değeri geçerli bir sayı değil: " + tracking.getRate().getRate());
            }
        } else {
            throw new RuntimeException("Yeni atama için rate değeri null olamaz.");
        }

        // Yeni atama eklenirse 1.0'ı geçerse hata veriyor
        if (currentTotalAllocation > 1.0) {
            throw new RuntimeException("Bir kullanıcının bir ay içindeki toplam atama miktarı 1.0'ı geçemez.");
        }

        existingTracking.setUser(tracking.getUser());
        existingTracking.setTaskPackage(tracking.getTaskPackage());
        existingTracking.setRate(tracking.getRate());
        existingTracking.setMonth(tracking.getMonth());


        return monthlyTrackingRepo.save(existingTracking);
    }

    @Transactional
    @Override
    public void deleteTracking(Long id) {
        monthlyTrackingRepo.deleteById(id);
    }


    @Override
    public List<TaskPackage> getTaskPackagesForUser(Long userId) {
        List<MonthlyTracking> monthlyTrackings = monthlyTrackingRepo.findByUserId(userId);
        List<TaskPackage> taskPackages = new ArrayList<>();
        for (MonthlyTracking monthlyTracking : monthlyTrackings) {
            taskPackages.add(monthlyTracking.getTaskPackage());
        }
        return taskPackages;
    }

    @Override
    public List<Rate> getRates() {
        return rateRepo.findAll();
    }

    @Override
    public List<MonthlyTracking> findByUserIdAndMonth(Long userId, String month) {
        return monthlyTrackingRepo.findByUserIdAndMonth(userId, month);
    }

    @Override
    public MonthlyTracking saveTracking(MonthlyTracking tracking) {


        // Kullanıcının aynı ay içindeki mevcut atamalar
        List<MonthlyTracking> existingTrackings = monthlyTrackingRepo.findByUserIdAndMonth(tracking.getUser().getId(), tracking.getMonth());

        // Mevcut toplam atama miktarını hesaplama
        double currentTotalAllocation = existingTrackings.stream()
                .mapToDouble(mt -> {
                    if (mt.getRate() != null && mt.getRate().getRate() != null) {
                        try {
                            return Double.parseDouble(mt.getRate().getRate());
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Rate değeri geçerli bir sayı değil: " + mt.getRate().getRate());
                        }
                    } else {
                        return 0.0; // Rate null ise 0.0 döndür
                    }
                })
                .sum();

        // Yeni atamadan önce rate değeri kontrol ediyor
        if (tracking.getRate() != null && tracking.getRate().getRate() != null) {
            try {
                currentTotalAllocation += Double.parseDouble(tracking.getRate().getRate());
            } catch (NumberFormatException e) {
                throw new RuntimeException("Yeni atama için rate değeri geçerli bir sayı değil: " + tracking.getRate().getRate());
            }
        } else {
            throw new RuntimeException("Yeni atama için rate değeri null olamaz.");
        }

        // Yeni atama  1.0'ı geçerse hata gösteriyor
        if (currentTotalAllocation > 1.0) {
            throw new RuntimeException("Bir kullanıcının bir ay içindeki toplam atama miktarı 1.0'ı geçemez.");
        }


        return monthlyTrackingRepo.save(tracking);
    }


}