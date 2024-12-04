package com.example.App_www.controller;

import com.example.App_www.dto.RaportDto;
import com.example.App_www.dto.RegisterRaportRequestDto;
import com.example.App_www.model.Raport;
import com.example.App_www.repository.RaportRepository;
import com.example.App_www.service.RaportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RaportController {

    private final RaportService raportService;
    private final RaportRepository raportRepository;

    public RaportController(RaportService raportService, RaportRepository raportRepository) {
        this.raportService = raportService;
        this.raportRepository = raportRepository;
    }

    @PostMapping("/raporty/stworz")
    public ResponseEntity<Raport> stworzRaport(@RequestBody RegisterRaportRequestDto raportRequest) {
        Raport raport = raportService.stworzRaport(raportRequest);
        return ResponseEntity.ok(raport);
    }

    @GetMapping("/raporty/trener/{trenerId}")
    public ResponseEntity<List<RaportDto>> getRaportyForTrener(@PathVariable Long trenerId) {
        List<Raport> raporty = raportRepository.findByTrenerId(trenerId);

        List<RaportDto> raportDtos = raporty.stream()
                .map(raport -> new RaportDto(
                        raport.getNumerTygodnia(),
                        raport.getWaga(),
                        raport.getObwodBiodra(),
                        raport.getObwodPas(),
                        raport.getObwodTalia(),
                        raport.getObwodUdo(),
                        raport.getObwodBiceps(),
                        raport.getOdczuwanieTreningu(),
                        raport.getChecPodjarania(),
                        raport.getJakoscSnu(),
                        raport.getSamopoczucie(),
                        raport.getPoziomEnergii(),
                        raport.getDataRaportu(),
                        raport.getKlientImie(),
                        raport.getKlientNazwisko()
                ))
                .toList();

        return ResponseEntity.ok(raportDtos);
    }
}