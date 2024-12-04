package com.example.App_www.service;


import com.example.App_www.dto.RegisterRaportRequestDto;
import com.example.App_www.model.Klient;
import com.example.App_www.model.Raport;
import com.example.App_www.model.Trener;
import com.example.App_www.repository.KlientRepository;
import com.example.App_www.repository.RaportRepository;
import com.example.App_www.repository.TrenerRepository;
import org.springframework.stereotype.Service;

@Service
public class RaportService {

    private final RaportRepository raportRepository;
    private final KlientRepository klientRepository;
    private final TrenerRepository trenerRepository;

    public RaportService(RaportRepository raportRepository, KlientRepository klientRepository, TrenerRepository trenerRepository) {
        this.raportRepository = raportRepository;
        this.klientRepository = klientRepository;
        this.trenerRepository = trenerRepository;
    }

    public Raport stworzRaport(RegisterRaportRequestDto dto) {
        Klient klient = klientRepository.findById(dto.getKlientId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono klienta o ID: " + dto.getKlientId()));

        Trener trener = trenerRepository.findById(dto.getTrenerId())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono trenera o ID: " + dto.getTrenerId()));

        Raport raport = new Raport();
        raport.setKlient(klient);
        raport.setTrener(trener);
        raport.setNumerTygodnia(dto.getNumerTygodnia());
        raport.setWaga(dto.getWaga());
        raport.setObwodBiodra(dto.getObwodBiodra());
        raport.setObwodPas(dto.getObwodPas());
        raport.setObwodTalia(dto.getObwodTalia());
        raport.setObwodUdo(dto.getObwodUdo());
        raport.setObwodBiceps(dto.getObwodBiceps());
        raport.setOdczuwanieTreningu(dto.getOdczuwanieTreningu());
        raport.setChecPodjarania(dto.getChecPodjarania());
        raport.setJakoscSnu(dto.getJakoscSnu());
        raport.setSamopoczucie(dto.getSamopoczucie());
        raport.setPoziomEnergii(dto.getPoziomEnergii());
        raport.setDataRaportu(dto.getDataRaportu());
        raport.setKlientImie(klient.getImie());
        raport.setKlientNazwisko(klient.getNazwisko());

        return raportRepository.save(raport);
    }
}