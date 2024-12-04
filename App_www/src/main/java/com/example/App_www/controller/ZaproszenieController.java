package com.example.App_www.controller;

import com.example.App_www.dto.ZaproszenieDTO;
import com.example.App_www.model.Enums.StatusZaproszenia;
import com.example.App_www.model.Klient;
import com.example.App_www.model.Trener;
import com.example.App_www.model.Zaproszenie;
import com.example.App_www.service.KlientService;
import com.example.App_www.service.TrenerService;
import com.example.App_www.service.ZaproszenieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ZaproszenieController {

    private final ZaproszenieService zaproszenieService;
    private final TrenerService trenerService;
    private final KlientService klientService;

    public ZaproszenieController(ZaproszenieService zaproszenieService, TrenerService trenerService, KlientService klientService) {
        this.zaproszenieService = zaproszenieService;
        this.trenerService = trenerService;
        this.klientService = klientService;
    }

    @PostMapping("/zaproszenia/send")
    public ResponseEntity<String> wyslijZaproszenie(@RequestParam long trenerId, @RequestParam long klientId) {
        Trener trener = trenerService.findTrenerById(trenerId);
        Klient klient = klientService.findKlientById(klientId);
        Zaproszenie zaproszenie = zaproszenieService.wyslijZaproszenie(trener, klient);
        return ResponseEntity.ok("Zaproszenie wysłane z ID: " + zaproszenie.getId());
    }
    @PostMapping("/zaproszenia/sendKlient/klient")
    public ResponseEntity<String> wyslijZaproszenieDoTrenera(@RequestParam long klientId, @RequestParam long trenerId) {
        Klient klient = klientService.findKlientById(klientId);
        Trener trener = trenerService.findTrenerById(trenerId);
        Zaproszenie zaproszenie = zaproszenieService.wyslijZaproszenieDoTrenera(klient, trener);
        return ResponseEntity.ok("Zaproszenie wysłane z ID: " + zaproszenie.getId());
    }


    @GetMapping("/zaproszenia/klient/{klientId}")
    public ResponseEntity<List<ZaproszenieDTO>> znajdzZaproszeniaDlaKlienta(@PathVariable("klientId") long klientId) {
        List<ZaproszenieDTO> zaproszenia = zaproszenieService.znajdzZaproszeniaDlaKlienta(klientId);
        return ResponseEntity.ok(zaproszenia);
    }
    @GetMapping("/zaproszenia/trener/{trenerId}")
    public ResponseEntity<List<ZaproszenieDTO>> znajdzZaproszeniaDlaTrenera(@PathVariable("trenerId") long trenerId) {
        List<ZaproszenieDTO> zaproszenia = zaproszenieService.znajdzZaproszeniaDlaTrenera(trenerId);
        return ResponseEntity.ok(zaproszenia);
    }

    @PostMapping("/zaproszenia/{zaproszenieId}/accept")
    public ResponseEntity<String> acceptZaproszenie(@PathVariable Long zaproszenieId) {
        try {
            zaproszenieService.acceptInvitation(zaproszenieId);
            return ResponseEntity.ok("Zaproszenie zostało zaakceptowane.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}