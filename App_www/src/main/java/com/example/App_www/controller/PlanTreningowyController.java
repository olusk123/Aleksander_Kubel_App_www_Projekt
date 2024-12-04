package com.example.App_www.controller;


import com.example.App_www.dto.GetPlanTreningowyResponseDto;
import com.example.App_www.dto.RegisterPlanTreningowyRequestDto;
import com.example.App_www.model.PlanTreningowy;
import com.example.App_www.repository.PlanTreningowyRepository;
import com.example.App_www.service.PlanTreningowyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class PlanTreningowyController {

    private final PlanTreningowyService planTreningowyService;

    @Autowired
    public PlanTreningowyController(PlanTreningowyService planTreningowyService) {
        this.planTreningowyService = planTreningowyService;
    }
    @PostMapping("/PlanTreningowy")
    public ResponseEntity<PlanTreningowy> createPlanTreningowy(@RequestBody RegisterPlanTreningowyRequestDto dto) {
        planTreningowyService.createPlanTreningowy(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/AllPlanTrenigowy")
    public ResponseEntity<Map<String, List<GetPlanTreningowyResponseDto>>> getAllPlansTreningowy() {
        System.out.println("Method getAllPlans was called");
        Map<String, List<GetPlanTreningowyResponseDto>> response = Map.of("PlanTrenigowy", planTreningowyService.findAll());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/planTreningowy/{id}")
    public ResponseEntity<PlanTreningowy> getPlanTreningowy(@PathVariable("id") long id) {
        PlanTreningowy planTreningowy = planTreningowyService.findById(id);
        if (planTreningowy != null) {
            return ResponseEntity.ok(planTreningowy);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/PlanTrenigowy/{id}")
    public ResponseEntity<?> deletePlanTrenigowy(@PathVariable("id") long id) {
        planTreningowyService.deletePlanTreningowy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/PlanTrenigowy/{klientId}/{planTreningowyId}")
    public ResponseEntity<?> updatePlanTrenigowy(@PathVariable("klientId") long klientId, @PathVariable("planTreningowyId") long planTreningowyId) {
        planTreningowyService.addPlanTreningowy(planTreningowyId, klientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
