package com.example.nutritioncalculator.controller;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.domain.Meal;
import com.example.nutritioncalculator.service.MealService;
import com.example.nutritioncalculator.util.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controller enables to manage meals.
 *
 * @author Bartek Dec
 */
@Controller
public class MealController {

    private MealService mealService;
    private DateParser dateParser;

    @Autowired
    public MealController(MealService mealService,
                          DateParser dateParser) {
        this.mealService = mealService;
        this.dateParser = dateParser;
    }

    /**
     * This method displays form to create new meal.
     *
     * @param model Holder for model attributes.
     * @return Name of the html view containing form to create new meal and table
     * with all ingredients found for this meal.
     */
    @GetMapping("/meal")
    public String displayMeal(Model model) {
        model.addAttribute("ingredients", mealService.getIngredients());
        model.addAttribute("meal", new Meal());
        return "meal";
    }

    /**
     * This method validates provided input and creates new meal.
     *
     * @param meal   Request object from Http protocol.
     * @param result BindingResult validates request object and add any errors.
     * @param model  Holder for model attributes.
     * @return Name of the end point which displays main paige (input form used to search
     * ingredients in the external database), otherwise message informing about bad request.
     */
    @PostMapping("/meal")
    public String addMeal(@Validated Meal meal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ingredients", mealService.getIngredients());
            return "meal";
        }

        mealService.saveMeal(meal);
        return "redirect:/";
    }

    /**
     * This method displays table with all meals persisted in the database.
     *
     * @param model Holder for model attributes.
     * @return Name of the html view containing table with all meals persisted in the database.
     */
    @GetMapping("/meals")
    public String showAllMeals(Model model) {
        model.addAttribute("meals", mealService.findAll());
        return "meals";
    }

    /**
     * This method removes selected meal.
     *
     * @param id Identification number assigned to the meal by local database.
     * @return Name of the end point which displays table with all meals persisted in the database.
     */
    @GetMapping("/delete/meal/{id}")
    public String removeMeal(@PathVariable Long id) {
        mealService.removeMealById(id);
        return "redirect:/meals";
    }

    /**
     * This method displays edit form for selected meal and list of ingredients covered by this meal.
     *
     * @param id    Identification number assigned to the meal by local database.
     * @param model Holder for model attributes.
     * @return Name of the html view containing form to update selected meal.
     */
    @GetMapping("/edit/meal/{id}")
    public String displayEditMeal(@PathVariable Long id, Model model) {
        Meal foundMeal = mealService.findById(id);
        List<Ingredient> foundIngredients = foundMeal.getIngredients();

        model.addAttribute("meal", foundMeal);
        model.addAttribute("ingredients", foundIngredients);
        return "editMeal";
    }

    /**
     * This method validates provided input and updates selected meal.
     *
     * @param meal   Request object from Http protocol.
     * @param result BindingResult validates request object and add any errors.
     * @param model  Holder for model attributes.
     * @return Name of the end point which displays table with all persisted meals,
     * otherwise message informing about bad request.
     */
    @PostMapping("/meal/edit")
    public String modifyMeal(@Validated Meal meal, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ingredients", mealService.getIngredients());
            return "editMeal";
        }

        Optional<Meal> mealOptional = mealService.update(meal);

        if (mealOptional.isPresent()) {
            return "redirect:/meals";
        }

        return "resourceNotExist";
    }

    /**
     * This method displays all persisted meals within defined date range.
     *
     * @param from  Start date.
     * @param to    End date.
     * @param model Holder for model attributes.
     * @return Name of the html view containing all meals within specified range.
     */
    @PostMapping("/find/date")
    public String findByMealDate(@RequestParam(value = "from") String from,
                                 @RequestParam(value = "to") String to,
                                 Model model) {

        Optional<LocalDate> optionalFrom = dateParser.convertDate(from);
        Optional<LocalDate> optionalTo = dateParser.convertDate(to);

        if (optionalFrom.isPresent() && optionalTo.isPresent()) {
            model.addAttribute("meals", mealService.findByDate(optionalFrom.get(), optionalTo.get()));
        } else {
            model.addAttribute("notFoundInRange", "");
        }

        return "meals";
    }
}
