package com.example.App_www.controller;


import com.example.App_www.dto.GetGoalResponseDto;
import com.example.App_www.dto.RegisterGoalRequestDto;
import com.example.App_www.dto.RegisterGoalResponseDto;
import com.example.App_www.model.Goal;
import com.example.App_www.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class GoalController {
    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }
    @PostMapping("/goal")
    public ResponseEntity<Goal> createGoal(@RequestBody RegisterGoalResponseDto dto) {

        goalService.createGoal(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("klient/goal")
    public ResponseEntity<Void> createGoal(@RequestBody RegisterGoalRequestDto dto) {
        goalService.createGoalKlient(dto.getKlientId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/goal")
    public ResponseEntity<Map<String, List<GetGoalResponseDto>>> getAllGoals() {
        Map<String, List<GetGoalResponseDto>> response = Map.of("Goal", goalService.findAll());
        return ResponseEntity.ok(response);
    }
    @PutMapping("/goal/{klientId}/{goalId}")
    public ResponseEntity<?> updateGoal(@PathVariable("klientId") long klientId, @PathVariable("goalId") long goalId) {
        goalService.addGoal(klientId, goalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/goal/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable("id") long id) {
        goalService.deleteGoal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}/goals")
    public ResponseEntity<List<Goal>> getGoals(@PathVariable Long id) {
        List<Goal> goals = goalService.findGoalsByKlientId(id);
        System.out.println(goals);
        return ResponseEntity.ok(goals);
    }
    @PostMapping("/goal/{trenerId}/klient/{klientId}")
    public ResponseEntity<Void> createGoalForClient(
            @PathVariable Long trenerId,
            @PathVariable Long klientId,
            @RequestBody RegisterGoalResponseDto goalDto) {


        try {
            goalService.createGoalForClient(trenerId, klientId, goalDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
