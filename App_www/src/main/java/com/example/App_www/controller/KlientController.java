package com.example.App_www.controller;

import com.example.App_www.dto.GetKlientsResponseDto;
import com.example.App_www.dto.RegisterKlientRequestDto;
import com.example.App_www.dto.RegisterKlientTrenerRequestDto;
import com.example.App_www.dto.RegisterPlanTreningowyRequestDto;
import com.example.App_www.model.Klient;
import com.example.App_www.model.Trener;
import com.example.App_www.repository.KlientRepository;
import com.example.App_www.repository.TrenerRepository;
import com.example.App_www.service.GoalService;
import com.example.App_www.service.KlientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class KlientController {

    private final KlientService klientService;
    private final KlientRepository klientRepository;
    private final TrenerRepository trenerRepository;

    @Autowired
    public KlientController(KlientService klientService, KlientRepository klientRepository,TrenerRepository trenerRepository) {
        this.klientService = klientService;
        this.klientRepository = klientRepository;
        this.trenerRepository = trenerRepository;
    }


    @PostMapping("/klient")
    public ResponseEntity<Klient> createKlient(@RequestBody RegisterKlientRequestDto dto) {
        klientService.createKlient(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/klient/{trenerId}")
    public ResponseEntity<Long> TrenerCreateKlient(@RequestBody RegisterKlientTrenerRequestDto dto, @PathVariable Long trenerId) {
        Trener trener = trenerRepository.findById(trenerId)
                .orElseThrow(() -> new IllegalArgumentException("Trener o podanym ID nie istnieje"));

        Klient klient = new Klient();
        klient.setImie(dto.getImie());
        klient.setNazwisko(dto.getNazwisko());
        klient.setDaneKontaktowe(dto.getDaneKontaktowe());
        klient.setTrener(trener);
        Klient savedKlient = klientRepository.save(klient);
        return new ResponseEntity<>(savedKlient.getId(), HttpStatus.CREATED);
    }


    @DeleteMapping("/klient/{id}")
    public ResponseEntity<?> deleteKlient(@PathVariable("id") long id) {
        klientService.deleteKlient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/klient")
    public ResponseEntity<Map<String, List<GetKlientsResponseDto>>> getAllKlients() {
        Map<String, List<GetKlientsResponseDto>> response = Map.of("klient", klientService.findAll());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/klient/email/{email}")
    public ResponseEntity<Long> getKlientIdByEmail(@PathVariable String email) {
        Optional<Klient> klientOpt = klientRepository.findByEmail(email);
        return klientOpt.map(klient -> new ResponseEntity<>(klient.getId(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/klient/{klientId}/planTreningowy")
    public ResponseEntity<String> createPlanForKlient(@PathVariable("klientId") long klientId, @RequestBody RegisterPlanTreningowyRequestDto dto) {
        klientService.createPlanForKlient(klientId, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/klient/{klientId}/trener/{trenerId}")
    public ResponseEntity<?> updateTrener(@PathVariable("klientId") long klientId, @PathVariable("trenerId") long trenerId) {
        klientService.addTrener(trenerId, klientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}












