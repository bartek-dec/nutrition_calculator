package com.example.nutritioncalculator.repository;

import com.example.nutritioncalculator.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findAllByDateBetweenOrderByDateAsc(LocalDate from, LocalDate to);

    List<Meal> findByOrderByDateAsc();
}
