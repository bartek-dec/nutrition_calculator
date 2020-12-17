package com.example.nutritioncalculator.util;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.dto.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientUtilTest {

    private IngredientUtil ingredientUtil;
    private UserRequest userRequest;
    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredientUtil=new IngredientUtil();
        userRequest = new UserRequest();
        ingredient=new Ingredient();
    }

    @Test
    void should_update_ingredients_fields() {
        //given
        userRequest.setWeight("30");

        ingredient.setQuantity(100);
        ingredient.setEnergy(200);
        ingredient.setProtein(100);
        ingredient.setFat(55);
        ingredient.setCarbs(55);

        //when
        Ingredient receivedIngredient = ingredientUtil.updateIngredient(ingredient,userRequest);

        //then
        assertEquals(30, receivedIngredient.getQuantity());
        assertEquals(60, receivedIngredient.getEnergy());
        assertEquals(30, receivedIngredient.getProtein());
        assertEquals(16, receivedIngredient.getFat());
        assertEquals(16, receivedIngredient.getCarbs());
    }
}
