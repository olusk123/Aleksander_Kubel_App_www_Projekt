package com.example.App_www.service;

import com.example.App_www.dto.GetKlientsResponseDto;
import com.example.App_www.dto.RegisterKlientRequestDto;
import com.example.App_www.dto.RegisterKlientTrenerRequestDto;
import com.example.App_www.dto.RegisterPlanTreningowyRequestDto;
import com.example.App_www.model.Goal;
import com.example.App_www.model.Klient;
import com.example.App_www.model.PlanTreningowy;
import com.example.App_www.model.Trener;
import com.example.App_www.repository.GoalRepository;
import com.example.App_www.repository.PlanTreningowyRepository;
import com.example.App_www.repository.KlientRepository;
import com.example.App_www.repository.TrenerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class KlientService {

    private final KlientRepository klientRepository;

    private final TrenerRepository trenerRepository;

    private final GoalRepository goalRepository;

    private final PlanTreningowyRepository planTreningowyRepository;

    @Autowired
    public KlientService(KlientRepository klientRepository,
                         TrenerRepository trenerRepository,
                         GoalRepository goalRepository,
                         PlanTreningowyRepository planTreningowyRepository) {
        this.klientRepository = klientRepository;
        this.trenerRepository = trenerRepository;
        this.goalRepository = goalRepository;
        this.planTreningowyRepository = planTreningowyRepository;
    }



    public List<GetKlientsResponseDto> findAll() {
        List<Klient> klients= klientRepository.findAll();
        List<GetKlientsResponseDto> dtos = new ArrayList<>();
        for (Klient klient : klients) {
            dtos.add(new GetKlientsResponseDto(
                    klient.getId(),
                    klient.getImie(),
                    klient.getNazwisko()
            ));
        }
        return dtos;
    }
    public Klient findKlientById(Long id) {
        return klientRepository.findById(id).orElse(null);
    }


    public void createPlanForKlient(Long klientId, RegisterPlanTreningowyRequestDto dto) {

        Klient klient = klientRepository.findById(klientId)
                .orElseThrow(() -> new RuntimeException("Klient nie znaleziony"));

        PlanTreningowy plan = new PlanTreningowy();
        plan.setDzienTygodnia(dto.getDzienTygodnia());
        plan.setCwiczenia(dto.getCwiczenia());
        plan.setIloscSerii(dto.getIloscSerii());
        plan.setZakresPowtorzen(dto.getZakresPowtorzen());
        plan.setObciazenie(dto.getObciazenie());
        plan.setPrzerwy(dto.getPrzerwy());

        planTreningowyRepository.save(plan);
        klient.addPlanTreningowy(plan);
        klientRepository.save(klient);
    }

    public void createKlient(RegisterKlientRequestDto dto) {
        Klient klient = new Klient();
        klient.setEmail(dto.getEmail());
        klient.setImie(dto.getImie());
        klient.setNazwisko(dto.getNazwisko());
        klient.setPassword(dto.getPassword());
        klient.setDaneKontaktowe(dto.getDaneKontaktowe());
        klientRepository.save(klient);
    }


    @Transactional
    public void deleteKlient(Long id) {
        Klient klient = klientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Klient not found")
        );
        List<Goal> goals = new ArrayList<>(klient.getGoals());
        for (Goal goal : goals) {
            goal.setKlient(null);
            goalRepository.delete(goal);
        }
    }

    @Transactional
    public void addTrener(Long trenerId, Long klientId) {
        System.out.println("Client " + klientId + " is adding trainer " + trenerId);
        Klient klient = klientRepository.findById(klientId).orElseThrow(
                () -> new EntityNotFoundException("Klient not found")
        );
        Trener trener = trenerRepository.findById(trenerId).orElseThrow(
                () -> new EntityNotFoundException("Trener not found")
        );

        klient.setTrener(trener);

        if (!trener.getPodopieczni().contains(klient)) {
            trener.getPodopieczni().add(klient);
        }
        klientRepository.save(klient);
        trenerRepository.save(trener);
    }





}