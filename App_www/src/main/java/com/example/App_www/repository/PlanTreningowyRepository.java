package com.example.App_www.repository;

import com.example.App_www.model.PlanTreningowy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanTreningowyRepository extends JpaRepository<PlanTreningowy, Long> {
}