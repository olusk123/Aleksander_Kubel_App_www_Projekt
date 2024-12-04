package com.example.App_www.repository;

import com.example.App_www.model.Raport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaportRepository extends JpaRepository<Raport, Long> {
    List<Raport> findByTrenerId(Long trenerId);
}