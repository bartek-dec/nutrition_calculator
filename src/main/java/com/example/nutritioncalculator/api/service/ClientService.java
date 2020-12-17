package com.example.nutritioncalculator.api.service;

import com.example.nutritioncalculator.domain.Ingredient;

import java.util.List;

public interface ClientService {

    List<Ingredient> getIngredients(String ingredient, int weight);
}
