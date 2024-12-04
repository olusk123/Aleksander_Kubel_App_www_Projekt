package com.example.App_www.repository;

import com.example.App_www.model.Zaproszenie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZaproszenieRepository extends JpaRepository<Zaproszenie, Long> {
    List<Zaproszenie> findByKlientId(Long klientId);
    List<Zaproszenie> findByTrenerId(Long Trenerd);
}