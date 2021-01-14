package com.example.nutritioncalculator.service;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.domain.Meal;
import com.example.nutritioncalculator.repository.IngredientRepository;
import com.example.nutritioncalculator.repository.MealRepository;
import com.example.nutritioncalculator.util.IngredientUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealServiceImplTest {

    @Mock
    private MealRepository mealRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private IngredientUtil ingredientUtil;
    @InjectMocks
    private MealServiceImpl mealService;

    @Test
    void should_add_an_Ingredient_to_in_memory_list() {
        //given
        Ingredient ingredient = new Ingredient();

        //when
        mealService.addIngredient(ingredient);

        //then
        assertEquals(1, mealService.getIngredients().size());
    }

    @Test
    void should_return_in_memory_list_of_Ingredients() {
        //given
        Ingredient ingredient = new Ingredient();
        mealService.addIngredient(ingredient);

        //when
        List<Ingredient> ingredients = mealService.getIngredients();

        //then
        assertNotNull(ingredients);
        assertEquals(1, ingredients.size());
    }

    @Test
    void should_save_the_Meal() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(100);
        ingredient.setEnergy(200);
        ingredient.setProtein(100);
        ingredient.setFat(55);
        ingredient.setCarbs(55);

        mealService.addIngredient(ingredient);

        Meal meal = new Meal();

        Meal savedMeal = new Meal();
        savedMeal.setTotalEnergy(200);
        savedMeal.setTotalProtein(100);
        savedMeal.setTotalFat(55);
        savedMeal.setTotalCarbs(55);

        when(mealRepository.save(any(Meal.class))).thenReturn(savedMeal);

        //when
        Meal returnedMeal = mealService.saveMeal(meal);

        //then
        assertEquals(0, mealService.getIngredients().size());
        assertEquals(200, returnedMeal.getTotalEnergy());
        assertEquals(100, returnedMeal.getTotalProtein());
        assertEquals(55, returnedMeal.getTotalFat());
        assertEquals(55, returnedMeal.getTotalCarbs());
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void should_return_all_Meals() {
        //given
        Meal meal1 = new Meal();
        Meal meal2 = new Meal();
        List<Meal> meals = new ArrayList<>();
        meals.add(meal1);
        meals.add(meal2);

        when(mealRepository.findByOrderByDateAsc()).thenReturn(meals);

        //when
        List<Meal> foundMeals = mealService.findAll();

        //then
        assertEquals(2, foundMeals.size());
        verify(mealRepository, times(1)).findByOrderByDateAsc();
    }

    @Test
    void should_remove_Ingredient_from_in_memory_list() {
        //given
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setFoodId("1");
        ingredient1.setQuantity(100);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setFoodId("1");
        ingredient2.setQuantity(50);

        mealService.addIngredient(ingredient1);
        mealService.addIngredient(ingredient2);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setFoodId("1");
        ingredient3.setQuantity(50);

        //when
        mealService.removeIngredient(ingredient3);

        //then
        assertEquals(1, mealService.getIngredients().size());
        assertEquals(100, mealService.getIngredients().get(0).getQuantity());
    }

    @Test
    void should_delete_Meal() {
        //when
        mealService.removeMealById(1L);

        //then
        verify(mealRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void should_find_Meal_by_id() {
        //given
        Meal meal = new Meal();

        when(mealRepository.findById(anyLong())).thenReturn(Optional.of(meal));

        //when
        Meal foundMeal = mealService.findById(1L);

        //then
        assertNotNull(foundMeal);
        verify(mealRepository, times(1)).findById(anyLong());
    }

    @Test
    void should_update_Meal() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setQuantity(100);
        ingredient.setEnergy(200);
        ingredient.setProtein(100);
        ingredient.setFat(55);
        ingredient.setCarbs(55);

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);

        Meal foundMeal = new Meal();
        foundMeal.setIngredients(ingredients);

        Meal meal = new Meal();
        meal.setId(1L);
        meal.setName("Some meal");
        meal.setDate(LocalDate.of(2020, 12, 12));
        meal.setTotalEnergy(200);
        meal.setTotalProtein(100);
        meal.setTotalFat(55);
        meal.setTotalCarbs(55);

        when(mealRepository.findById(anyLong())).thenReturn(Optional.of(foundMeal));
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        //when
        Meal updatedMeal = mealService.update(meal).get();

        //then
        assertNotNull(updatedMeal);
        assertEquals("Some meal", updatedMeal.getName());
        assertEquals(200, updatedMeal.getTotalEnergy());
        assertEquals(100, updatedMeal.getTotalProtein());
        assertEquals(55, updatedMeal.getTotalFat());
        assertEquals(55, updatedMeal.getTotalCarbs());
    }


    @Test
    void should_return_Meals_in_given_date_range() {
        //given
        LocalDate from = LocalDate.of(2020, 12, 10);
        LocalDate to = LocalDate.of(2020, 12, 14);
        List<Meal> meals = new ArrayList<>();
        meals.add(new Meal());

        when(mealRepository.findAllInRange(any(LocalDate.class), any(LocalDate.class))).thenReturn(meals);

        //when
        mealService.findByDate(from, to);

        //then
        verify(mealRepository, times(1)).findAllInRange(any(LocalDate.class), any(LocalDate.class));
    }
}
