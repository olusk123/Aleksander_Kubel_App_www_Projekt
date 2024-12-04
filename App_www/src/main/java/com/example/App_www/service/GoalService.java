package com.example.App_www.service;


import com.example.App_www.dto.GetGoalResponseDto;
import com.example.App_www.dto.RegisterGoalRequestDto;
import com.example.App_www.dto.RegisterGoalResponseDto;
import com.example.App_www.model.Goal;
import com.example.App_www.model.Klient;
import com.example.App_www.model.Trener;
import com.example.App_www.repository.GoalRepository;
import com.example.App_www.repository.KlientRepository;
import com.example.App_www.repository.TrenerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final KlientRepository klientRepository;
    private final TrenerRepository trenerRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository, KlientRepository klientRepository, TrenerRepository trenerRepository) {
        this.goalRepository = goalRepository;
        this.klientRepository = klientRepository;
        this.trenerRepository = trenerRepository;
    }
    public List<GetGoalResponseDto> findAll() {
        List<Goal> goals = goalRepository.findAll();
        List<GetGoalResponseDto> dtos = new ArrayList<>();
        for (Goal goal : goals) {
            dtos.add(new GetGoalResponseDto(
                    goal.getId(),
                    goal.getOpis(),
                    goal.getGoalType(),
                    goal.getStartDate(),
                    goal.getEndDate()
            ));
        }
        return dtos;
    }
    public void createGoal(RegisterGoalResponseDto dto) {

        Goal goal = new Goal();
        goal.setOpis(dto.getOpis());
        goal.setGoalType(dto.getGoalType());
        goal.setStartDate(dto.getStartDate());
        goal.setEndDate(dto.getEndDate());
        goalRepository.save(goal);
    }
    public void createGoalKlient(Long clientId, RegisterGoalRequestDto dto) {
        Klient klient = klientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono klienta o podanym ID"));

        Goal goal = new Goal();
        goal.setOpis(dto.getOpis());
        goal.setGoalType(dto.getGoalType());
        goal.setStartDate(dto.getStartDate());
        goal.setEndDate(dto.getEndDate());

        goal.setKlient(klient);

        goalRepository.save(goal);
    }

    public void createGoalForClient(Long trenerId, Long klientId, RegisterGoalResponseDto goalDto) {

        Trener trener = trenerRepository.findById(trenerId)
                .orElseThrow(() -> {
                    System.out.println("Błąd: Trener o ID " + trenerId + " nie istnieje");
                    return new IllegalArgumentException("Trener o podanym ID nie istnieje");
                });

        Klient klient = klientRepository.findById(klientId)
                .orElseThrow(() -> {
                    System.out.println("Błąd: Klient o ID " + klientId + " nie istnieje");
                    return new IllegalArgumentException("Klient o podanym ID nie istnieje");
                });

        Goal goal = new Goal();
        goal.setOpis(goalDto.getOpis());
        goal.setGoalType(goalDto.getGoalType());
        goal.setStartDate(goalDto.getStartDate());
        goal.setEndDate(goalDto.getEndDate());
        klient.addGoal(goal);
        goalRepository.save(goal);
    }

    @Transactional
    public void addGoal(Long klientId, Long goalId) {
        Klient klient = klientRepository.findById(klientId).orElseThrow(
                () -> new EntityNotFoundException("Klient not found")
        );

        Goal goal = goalRepository.findById(goalId).orElseThrow(
                () -> new EntityNotFoundException("Goal not found")
        );
        goal.setKlient(klient);
        klient.getGoals().add(goal);
        goalRepository.save(goal);
        klientRepository.save(klient);
    }
    @Transactional
    public void deleteGoal(Long id) {
        Goal goal = goalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Goal not found")
        );
        Klient klient = goal.getKlient();
        if (klient != null) {
            klient.setGoals(null);
        }

        goalRepository.delete(goal);
    }
    public List<Goal> findGoalsByKlientId(Long klientId) {
        Klient klient = klientRepository.findById(klientId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono klienta o ID: " + klientId));

        List<Goal> goals = klient.getGoals();
        if (goals == null || goals.isEmpty()) {
            throw new RuntimeException("Cele klienta są puste lub null");
        }

        goals.forEach(goal -> System.out.println("Cel: " + goal));

        return goals;
    }


}
