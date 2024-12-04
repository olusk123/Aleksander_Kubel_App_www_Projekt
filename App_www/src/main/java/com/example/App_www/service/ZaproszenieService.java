package com.example.App_www.service;

import com.example.App_www.dto.ZaproszenieDTO;
import com.example.App_www.model.Enums.StatusZaproszenia;
import com.example.App_www.model.Klient;
import com.example.App_www.model.Trener;
import com.example.App_www.model.Zaproszenie;
import com.example.App_www.repository.TrenerRepository;
import com.example.App_www.repository.ZaproszenieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZaproszenieService {

    private final ZaproszenieRepository zaproszenieRepository;
    private final TrenerRepository trenerRepository;

    public ZaproszenieService(ZaproszenieRepository zaproszenieRepository, TrenerRepository trenerRepository) {
        this.zaproszenieRepository = zaproszenieRepository;
        this.trenerRepository = trenerRepository;
    }

    public Zaproszenie wyslijZaproszenie(Trener trener, Klient klient) {
        Zaproszenie zaproszenie = new Zaproszenie();
        zaproszenie.setTrener(trener);
        zaproszenie.setKlient(klient);
        zaproszenie.setStatus(StatusZaproszenia.PENDING);
        return zaproszenieRepository.save(zaproszenie);
    }
    public Zaproszenie wyslijZaproszenieDoTrenera(Klient klient, Trener trener) {
        Zaproszenie zaproszenie = new Zaproszenie();
        zaproszenie.setKlient(klient);
        zaproszenie.setTrener(trener);
        zaproszenie.setStatus(StatusZaproszenia.PENDING);
        return zaproszenieRepository.save(zaproszenie);
    }


    public List<ZaproszenieDTO> znajdzZaproszeniaDlaKlienta(Long klientId) {
        List<Zaproszenie> zaproszenia = zaproszenieRepository.findByKlientId(klientId);
        return getZaproszenieDTOS(zaproszenia);
    }
    public List<ZaproszenieDTO> znajdzZaproszeniaDlaTrenera(Long trenerId) {
        List<Zaproszenie> zaproszenia = zaproszenieRepository.findByTrenerId(trenerId);
        return getZaproszenieDTOS(zaproszenia);
    }

    private List<ZaproszenieDTO> getZaproszenieDTOS(List<Zaproszenie> zaproszenia) {
        return zaproszenia.stream()
                .map(z -> {
                    ZaproszenieDTO dto = new ZaproszenieDTO();
                    dto.setId(z.getId());
                    dto.setTrenerImie(z.getTrener().getImie());
                    dto.setTrenerNazwisko(z.getTrener().getNazwisko());
                    dto.setKlientImie(z.getKlient().getImie());
                    dto.setKlientNazwisko(z.getKlient().getNazwisko());
                    dto.setStatus(z.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void acceptInvitation(Long zaproszenieId) {
        Zaproszenie zaproszenie = zaproszenieRepository.findById(zaproszenieId)
                .orElseThrow(() -> new IllegalArgumentException("Zaproszenie nie istnieje"));


        if (zaproszenie.getStatus() != StatusZaproszenia.PENDING) {
            throw new IllegalStateException("Zaproszenie nie jest oczekujące");
        }

        zaproszenie.setStatus(StatusZaproszenia.ACCEPTED);
        zaproszenieRepository.save(zaproszenie);

        Klient klient = zaproszenie.getKlient();
        Trener trener = zaproszenie.getTrener();
        klient.setTrener(trener);
        trener.getPodopieczni().add(klient);
        trenerRepository.save(trener);
    }
    public Zaproszenie getZaproszenieById(Long zaproszenieId) {
        return zaproszenieRepository.findById(zaproszenieId)
                .orElseThrow(() -> new RuntimeException("Zaproszenie nie istnieje"));
    }

}