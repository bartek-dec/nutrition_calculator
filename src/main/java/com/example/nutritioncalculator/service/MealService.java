package com.example.nutritioncalculator.service;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.domain.Meal;
import com.example.nutritioncalculator.dto.UserRequest;

import java.time.LocalDate;
import java.util.List;

public interface MealService {

    void addIngredient(Ingredient ingredient);

    List<Ingredient> getIngredients();

    Meal saveMeal(Meal meal);

    List<Meal> findAll();

    void removeIngredient(Ingredient ingredient);

    void removeMealById(Long id);

    Meal findById(Long id);

    Meal update(Meal meal);

    void updateIngredient(UserRequest userRequest);

    List<Meal> findByDate(LocalDate from, LocalDate to);
}
