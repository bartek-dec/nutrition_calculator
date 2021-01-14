package com.example.nutritioncalculator.service;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.domain.Meal;
import com.example.nutritioncalculator.dto.UserRequest;
import com.example.nutritioncalculator.repository.IngredientRepository;
import com.example.nutritioncalculator.repository.MealRepository;
import com.example.nutritioncalculator.util.IngredientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * MealServiceImpl communicates with persistence layer.
 *
 * @author Bartek Dec
 */
@Service
public class MealServiceImpl implements MealService {

    private MealRepository mealRepository;
    private IngredientRepository ingredientRepository;
    private IngredientUtil ingredientUtil;
    private List<Ingredient> ingredients;

    @Autowired
    public MealServiceImpl(MealRepository mealRepository,
                           IngredientRepository ingredientRepository,
                           IngredientUtil ingredientUtil) {
        this.mealRepository = mealRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientUtil = ingredientUtil;
        this.ingredients = new ArrayList<>();
    }

    /**
     * This method adds ingredient to the (in memory) list. All Ingredients from the
     * list will be assigned to the meal when created.
     *
     * @param ingredient New ingredient added to the list.
     */
    @Override
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    /**
     * This method returns (in memory) list of ingredients for this new meal.
     *
     * @return List with assigned ingredients.
     */
    @Override
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * This method sends Meal and assigned Ingredients to persistence layer
     * to persist in database.
     *
     * @param meal Object to persist in database.
     */
    @Transactional
    @Override
    public Meal saveMeal(Meal meal) {
        meal.setTotalEnergy(getTotalEnergy(ingredients));
        meal.setTotalProtein(getTotalProtein(ingredients));
        meal.setTotalFat(getTotalFat(ingredients));
        meal.setTotalCarbs(getTotalCarbs(ingredients));

        Meal savedMeal = mealRepository.save(meal);
        ingredients.forEach(element -> element.setMeal(savedMeal));
        ingredientRepository.saveAll(ingredients);

        ingredients.clear();
        return savedMeal;
    }

    /**
     * This method gets all Meals from persistence layer.
     *
     * @return List of Meals.
     */
    @Override
    public List<Meal> findAll() {
        return mealRepository.findByOrderByDateAsc();
    }

    /**
     * This method removes requested Ingredient from the (in memory) list.
     *
     * @param ingredient Object to remove from (in memory) list.
     */
    @Override
    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    /**
     * This method invokes deleteById method on persistence layer to delete
     * object from database.
     *
     * @param id Request parameter to delete Meal with provided id.
     */
    @Override
    public void removeMealById(Long id) {
        mealRepository.deleteById(id);
    }

    /**
     * This method invokes findById method on persistence layer to return
     * Meal with requested id.
     *
     * @param id Request parameter to find Meal with provided id.
     * @return Found Meal object.
     */
    @Override
    public Meal findById(Long id) {
        return mealRepository.findById(id).get();
    }

    /**
     * This method gets Meal from persistence layer and updates it's fields.
     *
     * @param meal Request object from Http protocol.
     */
    @Override
    public Optional<Meal> update(Meal meal) {
        Optional<Meal> mealOptional = mealRepository.findById(meal.getId());

        if (mealOptional.isPresent()) {
            Meal foundMeal = mealOptional.get();

            List<Ingredient> foundIngredients = foundMeal.getIngredients();

            foundMeal.setName(meal.getName());
            foundMeal.setDate(meal.getDate());
            foundMeal.setTotalEnergy(getTotalEnergy(foundIngredients));
            foundMeal.setTotalProtein(getTotalProtein(foundIngredients));
            foundMeal.setTotalFat(getTotalFat(foundIngredients));
            foundMeal.setTotalCarbs(getTotalCarbs(foundIngredients));

            Meal updatedMeal = mealRepository.save(foundMeal);

            return Optional.of(updatedMeal);
        }
        return Optional.empty();
    }

    /**
     * This method finds Ingredient in the (in memory) list and updates it's fields.
     *
     * @param userRequest Request object from Http protocol.
     */
    @Override
    public Optional<Ingredient> updateIngredient(UserRequest userRequest) {
        Optional<Ingredient> ingredientOptional = ingredients.stream()
                .filter(element -> Objects.equals(element.getFoodId(), userRequest.getFoodId()))
                .filter(element -> Objects.equals(element.getName(), userRequest.getProduct()))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient foundIngredient = ingredientOptional.get();
            Ingredient updatedIngredient = ingredientUtil.updateIngredient(foundIngredient, userRequest);

            return Optional.of(updatedIngredient);
        }

        return Optional.empty();
    }

    /**
     * This method invokes findByDate method on persistence layer to return all
     * Meals within requested date range.
     *
     * @param from Start date.
     * @param to   End date.
     * @return List of Meals.
     */
    @Override
    public List<Meal> findByDate(LocalDate from, LocalDate to) {
        return mealRepository.findAllByDateBetweenOrderByDateAsc(from, to);
    }

    /**
     * This method calculates sum of energy of the all ingredients kept
     * in the (in memory) list.
     *
     * @param ingredients In memory list of Ingredients.
     * @return Sum of energy calculated for all ingredients.
     */
    private Integer getTotalEnergy(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getEnergy)
                .reduce(0, Integer::sum);
    }

    /**
     * This method calculates sum of protein of the all ingredients kept
     * in the (in memory) list.
     *
     * @param ingredients In memory list of Ingredients.
     * @return Sum of protein calculated for all ingredients.
     */
    private Integer getTotalProtein(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getProtein)
                .reduce(0, Integer::sum);
    }

    /**
     * This method calculates sum of fat of the all ingredients kept
     * in the (in memory) list.
     *
     * @param ingredients In memory list of Ingredients.
     * @return Sum of fat calculated for all ingredients.
     */
    private Integer getTotalFat(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getFat)
                .reduce(0, Integer::sum);
    }

    /**
     * This method calculates sum of carbs of the all ingredients kept
     * in the (in memory) list.
     *
     * @param ingredients In memory list of Ingredients.
     * @return Sum of carbs calculated for all ingredients.
     */
    private Integer getTotalCarbs(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(Ingredient::getCarbs)
                .reduce(0, Integer::sum);
    }
}
