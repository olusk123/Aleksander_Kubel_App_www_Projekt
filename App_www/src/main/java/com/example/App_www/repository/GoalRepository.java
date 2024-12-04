package com.example.App_www.repository;

import com.example.App_www.model.Goal;
import com.example.App_www.model.Klient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    void deleteAllByKlient(Klient klient);
}
