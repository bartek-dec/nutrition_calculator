package com.example.nutritioncalculator.service;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.domain.Meal;
import com.example.nutritioncalculator.dto.UserRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealService {

    void addIngredient(Ingredient ingredient);

    List<Ingredient> getIngredients();

    Meal saveMeal(Meal meal);

    List<Meal> findAll();

    void removeIngredient(Ingredient ingredient);

    void removeMealById(Long id);

    Meal findById(Long id);

    Optional<Meal> update(Meal meal);

    Optional<Ingredient> updateIngredient(UserRequest userRequest);

    List<Meal> findByDate(LocalDate from, LocalDate to);
}
