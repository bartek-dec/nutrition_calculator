package com.example.nutritioncalculator.util;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.dto.UserRequest;
import org.springframework.stereotype.Service;

/**
 * Service updates state of Ingredient instance.
 *
 * @author Bartek Dec
 */
@Service
public class IngredientUtil {

    /**
     * This method updates state of the Ingredient instance.
     *
     * @param ingredient     Ingredient which will be updated.
     * @param userRequest Request object from Http protocol.
     * @return Updated instance of the ingredient.
     */
    public Ingredient updateIngredient(Ingredient ingredient, UserRequest userRequest) {

        Integer energy = updateValue(userRequest.getWeight(), ingredient.getQuantity(), ingredient.getEnergy());
        Integer protein = updateValue(userRequest.getWeight(), ingredient.getQuantity(), ingredient.getProtein());
        Integer fat = updateValue(userRequest.getWeight(), ingredient.getQuantity(), ingredient.getFat());
        Integer carbs = updateValue(userRequest.getWeight(), ingredient.getQuantity(), ingredient.getCarbs());

        ingredient.setEnergy(energy);
        ingredient.setProtein(protein);
        ingredient.setFat(fat);
        ingredient.setCarbs(carbs);
        ingredient.setQuantity(Integer.parseInt(userRequest.getWeight()));

        return ingredient;
    }

    /**
     * This method updates selected nutrition value as a proportion of the
     * new weight to the current weight and multiplied by the selected nutrition.
     *
     * @param newWeight     New weight extracted Request object from Http protocol.
     * @param currentWeight Current weight.
     * @param nutrition     Nutrition value to be updated.
     * @return Integer value of the updated nutrition value.
     */
    private int updateValue(String newWeight, Integer currentWeight, Integer nutrition) {
        double newValue = Double.parseDouble(newWeight);
        double result = (newValue / currentWeight) * nutrition;
        return (int) result;
    }
}
