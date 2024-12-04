package com.example.App_www.controller;

import com.example.App_www.dto.GetTrenersResponseDto;
import com.example.App_www.dto.RegisterTrenerRequestDto;
import com.example.App_www.model.Klient;
import com.example.App_www.model.Trener;
import com.example.App_www.repository.TrenerRepository;
import com.example.App_www.service.TrenerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TrenerController {


    private final TrenerService trenerService;
    private final TrenerRepository trenerRepository;

    @Autowired
    public TrenerController(TrenerService trenerService, TrenerRepository trenerRepository) {
        this.trenerService = trenerService;
        this.trenerRepository = trenerRepository;
    }


    @PostMapping("/trener")
    public ResponseEntity<Trener> createTrener(@RequestBody RegisterTrenerRequestDto dto) {
        trenerService.createTrener(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/trener")
    public ResponseEntity<Map<String, List<GetTrenersResponseDto>>> getAllTreners() {
        Map<String, List<GetTrenersResponseDto>> response = Map.of("Treners", trenerService.findAll());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/trener/{id}")
    public ResponseEntity<?> deleteTrener(@PathVariable("id") long id) {
        trenerService.deleteTrener(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/trener/email/{email}")
    public ResponseEntity<Long> getTrenerIdByEmail(@PathVariable String email) {
        Optional<Trener> trenerOpt = trenerRepository.findByEmail(email);
        return trenerOpt.map(trener -> new ResponseEntity<>(trener.getId(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/trener/{trenerId}/{klientId}")
    public ResponseEntity<?> updateTrener(@PathVariable("trenerId") long trenerId, @PathVariable("klientId") long klientId) {
        trenerService.addKlient(trenerId, klientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("trener/zlicz/{id}")
    public ResponseEntity<Integer> zliczklientow(@PathVariable("id") Long id) {
        Integer count = trenerService.zliczKlientow(id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("trener/{id}/klienci")
    public ResponseEntity<List<Klient>> getPodopieczni(@PathVariable Long id) {
        List<Klient> podopieczni = trenerService.findPodopieczniByTrenerId(id);
        return ResponseEntity.ok(podopieczni);
    }


}
