package com.example.nutritioncalculator.service;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.dto.UserRequest;
import com.example.nutritioncalculator.repository.IngredientRepository;
import com.example.nutritioncalculator.util.IngredientUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private IngredientUtil ingredientUtil;
    @InjectMocks
    private IngredientServiceImpl ingredientService;

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient();
    }

    @Test
    void should_delete_an_Ingredient() {
        //when
        ingredientService.deleteById(1L);

        //then
        verify(ingredientRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void should_return_Ingredient_object_found_by_id() {
        //given
        ingredient.setId(1L);

        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));

        //when
        Ingredient foundIngredient = ingredientService.findById(1L);

        //then
        assertNotNull(foundIngredient);
        assertEquals(1L, foundIngredient.getId());
        verify(ingredientRepository, times(1)).findById(anyLong());
    }

    @Test
    void should_update_an_Ingredient() {
        //given
        ingredient.setId(1L);

        Ingredient foundIngredient = new Ingredient();
        foundIngredient.setId(1L);

        Ingredient updatedIngredient = new Ingredient();
        updatedIngredient.setId(1L);

        UserRequest userRequest = new UserRequest();
        userRequest.setFoodDbId(1L);

        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(foundIngredient));
        when(ingredientUtil.updateIngredient(any(Ingredient.class), any(UserRequest.class))).thenReturn(ingredient);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(updatedIngredient);

        //when
        Optional<Ingredient> update = ingredientService.update(userRequest);

        //then
        assertEquals(1L, update.get().getId());
        verify(ingredientRepository, times(1)).findById(anyLong());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }
}
