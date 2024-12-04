package com.example.App_www.repository;

import com.example.App_www.model.Klient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KlientRepository extends JpaRepository<Klient, Long> {
    Optional<Klient> findByEmail(String email);


}
