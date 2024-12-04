package com.example.App_www.service;

import com.example.App_www.dto.GetTrenersResponseDto;
import com.example.App_www.dto.RegisterTrenerRequestDto;
import com.example.App_www.model.Klient;
import com.example.App_www.model.Trener;
import com.example.App_www.repository.KlientRepository;
import com.example.App_www.repository.TrenerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrenerService {
    private final TrenerRepository trenerRepository;
    private final KlientRepository klientRepository;

    @Autowired
    public TrenerService(TrenerRepository trenerRepository, KlientRepository klientRepository) {
        this.trenerRepository = trenerRepository;
        this.klientRepository = klientRepository;
    }

    public List<GetTrenersResponseDto> findAll() {
        List<Trener> treners = trenerRepository.findAll();
        List<GetTrenersResponseDto> dtos = new ArrayList<>();
        for (Trener trener : treners) {
            dtos.add(new GetTrenersResponseDto(
                    trener.getId(),
                    trener.getImie(),
                    trener.getNazwisko()
            ));
        }
        return dtos;
    }


    public void createTrener(RegisterTrenerRequestDto dto) {
        Trener trener = new Trener();
        trener.setEmail(dto.getEmail());
        trener.setImie(dto.getImie());
        trener.setNazwisko(dto.getNazwisko());
        trener.setPassword(dto.getPassword());
        trenerRepository.save(trener);
    }

    @Transactional
    public void deleteTrener(Long id) {
        Trener trener = trenerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Trener not found")
        );
        for (Klient klient: trener.getPodopieczni()) klient.setTrener(null);
        trenerRepository.delete(trener);

    }
    @Transactional
    public void addKlient(Long trenerId, Long klientId) {
        Klient klient = klientRepository.findById(klientId).orElseThrow(
                () -> new EntityNotFoundException("Klient not found")
        );
        Trener trener = trenerRepository.findById(trenerId).orElseThrow(
                () -> new EntityNotFoundException("Trener not found")
        );
        klient.setTrener(trener);
        trener.getPodopieczni().add(klient);
        trenerRepository.save(trener);
        klientRepository.save(klient);
    }

    public int zliczKlientow(Long Id) {

        Trener trener = trenerRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono trenera o ID: " + Id));

        return trener.getPodopieczni().size();
    }

    public List<Klient> findPodopieczniByTrenerId(Long trenerId) {
        System.out.println("Received trenerId dla listy: " + trenerId);
        Trener trener = trenerRepository.findById(trenerId)
                .orElseThrow(() -> new RuntimeException("Trener nie znaleziony: " + trenerId));
        return trener.getPodopieczni();
    }



    public Trener findTrenerById(Long id) {
        Optional<Trener> trener = trenerRepository.findById(id);
        return trener.orElseThrow(() -> new EntityNotFoundException("Trener o ID " + id + " nie został znaleziony."));
    }


}

