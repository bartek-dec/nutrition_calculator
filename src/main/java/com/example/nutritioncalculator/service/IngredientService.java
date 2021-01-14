package com.example.nutritioncalculator.service;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.dto.UserRequest;

import java.util.Optional;

public interface IngredientService {

    void deleteById(Long id);

    Ingredient findById(Long id);

    Optional<Ingredient> update(UserRequest userRequest);
}


