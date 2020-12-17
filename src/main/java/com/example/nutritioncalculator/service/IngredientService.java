package com.example.nutritioncalculator.service;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.dto.UserRequest;

public interface IngredientService {

    void deleteById(Long id);

    Ingredient findById(Long id);

    void update(UserRequest userRequest);
}


