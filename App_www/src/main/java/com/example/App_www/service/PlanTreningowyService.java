package com.example.App_www.service;

import com.example.App_www.dto.GetPlanTreningowyResponseDto;
import com.example.App_www.dto.RegisterPlanTreningowyRequestDto;
import com.example.App_www.model.Klient;
import com.example.App_www.model.PlanTreningowy;
import com.example.App_www.repository.KlientRepository;
import com.example.App_www.repository.PlanTreningowyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanTreningowyService {


    private final PlanTreningowyRepository planTreningowyRepository;
    private final KlientRepository klientRepository;

    @Autowired
    public PlanTreningowyService(PlanTreningowyRepository planTreningowyRepository, KlientRepository klientRepository) {
        this.planTreningowyRepository = planTreningowyRepository;
        this.klientRepository = klientRepository;
    }

    public PlanTreningowy findById(Long id) {
        Optional<PlanTreningowy> optionalPlan = planTreningowyRepository.findById(id);
        return optionalPlan.orElse(null);
    }

    public List<GetPlanTreningowyResponseDto> findAll() {
        List<PlanTreningowy> planTreningowys = planTreningowyRepository.findAll();
        List<GetPlanTreningowyResponseDto> dtos = new ArrayList<>();
        for (PlanTreningowy planTreningowy : planTreningowys) {
            dtos.add(new GetPlanTreningowyResponseDto(
                    planTreningowy.getId(),
                    planTreningowy.getDzienTygodnia(),
                    planTreningowy.getCwiczenia(),
                    planTreningowy.getIloscSerii(),
                    planTreningowy.getZakresPowtorzen(),
                    planTreningowy.getObciazenie(),
                    planTreningowy.getPrzerwy()
            ));
        }
        return dtos;
    }

    public void createPlanTreningowy(RegisterPlanTreningowyRequestDto dto) {
        PlanTreningowy planTreningowy = new PlanTreningowy();
        planTreningowy.setDzienTygodnia(dto.getDzienTygodnia());
        planTreningowy.setCwiczenia(dto.getCwiczenia());
        planTreningowy.setIloscSerii(dto.getIloscSerii());
        planTreningowy.setObciazenie(dto.getObciazenie());
        planTreningowy.setZakresPowtorzen(dto.getZakresPowtorzen());
        planTreningowy.setPrzerwy(dto.getPrzerwy());
        planTreningowyRepository.save(planTreningowy);

    }


    @Transactional
    public void deletePlanTreningowy(Long id) {
        PlanTreningowy planTreningowy = planTreningowyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Plan not found")
        );
        for (Klient klient : planTreningowy.getKlienci()) klient.setPlanyTreningowe(null);
        planTreningowyRepository.delete(planTreningowy);
    }

    @Transactional
    public void addPlanTreningowy(Long planTrenigowyId, Long klientId) {
        PlanTreningowy planTreningowy = planTreningowyRepository.findById(planTrenigowyId).orElseThrow(
                () -> new EntityNotFoundException("plan not found")
        );
        Klient klient = klientRepository.findById(klientId).orElseThrow(
                () -> new EntityNotFoundException("klient not found")
        );
        planTreningowy.getKlienci().add(klient);
        klient.getPlanyTreningowe().add(planTreningowy);
        klientRepository.save(klient);
        planTreningowyRepository.save(planTreningowy);
    }
}