package com.example.nutritioncalculator.service;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.dto.UserRequest;
import com.example.nutritioncalculator.repository.IngredientRepository;
import com.example.nutritioncalculator.util.IngredientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IngredientServiceImpl communicates with persistence layer.
 *
 * @author Bartek Dec
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private IngredientUtil ingredientUtil;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 IngredientUtil ingredientUtil) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientUtil = ingredientUtil;
    }

    /**
     * This method invokes deleteById method on persistence layer to delete
     * object from database.
     *
     * @param id Request parameter to delete Ingredient with provided id.
     */
    @Override
    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }

    /**
     * This method invokes findById method on persistence layer to return
     * Ingredient with requested id.
     *
     * @param id Request parameter to find Ingredient with provided id.
     * @return Found Ingredient object.
     */
    @Override
    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).get();
    }

    /**
     * This method gets Ingredient from persistence layer and updates its weight and nutrition values.
     *
     * @param userRequest Request object from Http protocol.
     */
    @Override
    public void update(UserRequest userRequest) {
        Ingredient foundIngredient = ingredientRepository.findById(userRequest.getFoodDbId()).get();

        foundIngredient = ingredientUtil.updateIngredient(foundIngredient, userRequest);
        ingredientRepository.save(foundIngredient);
    }
}
