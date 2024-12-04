package com.example.App_www.repository;

import com.example.App_www.model.Trener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrenerRepository extends JpaRepository<Trener, Long> {
    Optional<Trener> findByEmail(String email);

}

