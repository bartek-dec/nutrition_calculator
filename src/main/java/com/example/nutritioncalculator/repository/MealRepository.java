package com.example.nutritioncalculator.repository;

import com.example.nutritioncalculator.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    @Query(value = "SELECT * FROM meals WHERE date >= :from AND date <= :to", nativeQuery = true)
    List<Meal> findAllInRange(LocalDate from, LocalDate to);

    @Query(value = "SELECT * FROM meals ORDER BY date ASC", nativeQuery = true)
    List<Meal> findAll();
}
