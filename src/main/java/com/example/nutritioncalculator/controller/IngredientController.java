package com.example.nutritioncalculator.controller;

import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.dto.UserRequest;
import com.example.nutritioncalculator.service.IngredientService;
import com.example.nutritioncalculator.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;
import java.util.Optional;

/**
 * Controller enables to manage ingredients.
 *
 * @author Bartek Dec
 */
@Controller
public class IngredientController {

    private MealService mealService;
    private IngredientService ingredientService;

    @Autowired
    public IngredientController(MealService mealService,
                                IngredientService ingredientService) {
        this.mealService = mealService;
        this.ingredientService = ingredientService;
    }

    /**
     * This method removes selected ingredient from the service layer.
     *
     * @param id     Identification string assigned to the ingredient by external database.
     * @param weight mass of the ingredient defined by the user.
     * @return Name of the end point which displays table with the ingredients selected
     * for the meal (those ingredients are not yet persisted in the local database).
     */
    @GetMapping("/delete/{id}/{weight}")
    public String removeIngredient(@PathVariable String id, @PathVariable Integer weight) {
        Ingredient ingredient = mealService.getIngredients().stream()
                .filter(e -> Objects.equals(e.getFoodId(), id))
                .filter(e -> Objects.equals(e.getQuantity(), weight))
                .findFirst().get();

        mealService.removeIngredient(ingredient);
        return "redirect:/meal";
    }

    /**
     * This method displays edit form which is used to modify selected ingredient.
     *
     * @param id    Identification string assigned to the ingredient by external database.
     * @param model Holder for model attributes.
     * @return Name of the html view containing edit form.
     */
    @GetMapping("/edit/{id}")
    public String displayEditIngredient(@PathVariable String id, Model model) {
        Ingredient foundIngredient = mealService.getIngredients().stream()
                .filter(element -> Objects.equals(element.getFoodId(), id))
                .findFirst().get();

        UserRequest userRequest = new UserRequest();
        userRequest.setFoodId(foundIngredient.getFoodId());
        userRequest.setProduct(foundIngredient.getName());
        userRequest.setWeight(foundIngredient.getQuantity().toString());

        model.addAttribute("userRequest", userRequest);
        return "editIngredient";
    }

    /**
     * This method validates provided input and modifies selected ingredient.
     *
     * @param userRequest Request object from Http protocol.
     * @param result      BindingResult validates request object and add any errors.
     * @param model       Holder for model attributes.
     * @return Name of the end point which displays table filled with products
     * found for the current meal, otherwise message informing about bad request.
     */
    @PostMapping("/edit/ingredient")
    public String editIngredient(@Validated UserRequest userRequest, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "editIngredient";
        }

        try {
            int weight = Integer.parseInt(userRequest.getWeight());

            if (weight <= 0) {
                model.addAttribute("error", "Weight must be an integer greater than zero");
                return "editIngredient";
            }

            Optional<Ingredient> optionalIngredient = mealService.updateIngredient(userRequest);

            if (optionalIngredient.isPresent()) {
                return "redirect:/meal";
            }

            return "resourceNotExist";
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Weight must be an integer greater than zero");
            return "editIngredient";
        }
    }

    /**
     * This method displays edit form which is used to modify selected ingredient.
     *
     * @param id    Identification number assigned to the ingredient by local database.
     * @param model Holder for model attributes.
     * @return Name of the html view containing edit form.
     */
    @GetMapping("/edit/ingredient/{id}")
    public String displayEditIngredientDB(@PathVariable Long id, Model model) {
        Ingredient foundIngredient = ingredientService.findById(id);

        UserRequest userRequest = new UserRequest();
        userRequest.setMealDbId(foundIngredient.getMeal().getId());
        userRequest.setFoodDbId(foundIngredient.getId());
        userRequest.setProduct(foundIngredient.getName());
        userRequest.setWeight(foundIngredient.getQuantity().toString());

        model.addAttribute("userRequest", userRequest);
        return "editIngredientDB";
    }

    /**
     * This method validates provided input and modifies selected ingredient.
     *
     * @param userRequest Request object from Http protocol.
     * @param result      BindingResult validates request object and add any errors.
     * @param model       Holder for model attributes.
     * @return Name of the end point which displays table filled with products
     * found for the current meal, otherwise message informing about bad request.
     */
    @PostMapping("/edit/ingredientDB")
    public String editIngredientDB(@Validated UserRequest userRequest, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "editIngredientDB";
        }

        try {
            int weight = Integer.parseInt(userRequest.getWeight());

            if (weight <= 0) {
                model.addAttribute("error", "Weight must be an integer greater than zero");
                return "editIngredientDB";
            }

            Optional<Ingredient> optionalIngredient = ingredientService.update(userRequest);

            if (optionalIngredient.isPresent()) {
                Ingredient updatedIngredient = optionalIngredient.get();
                return "redirect:/edit/meal/" + updatedIngredient.getMeal().getId();
            }

            return "resourceNotExist";
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Weight must be an integer greater than zero");
            return "editIngredientDB";
        }
    }

    /**
     * This method removes selected ingredient from the persist layer.
     *
     * @param mealId Identification number assigned to the meal by the local database.
     * @param id     Identification number assigned to the ingredient by the local database.
     * @return Name of the end point which displays table with the ingredients for the
     * selected meal.
     */
    @GetMapping("/delete/ingredient/{mealId}/{id}")
    public String removeIngredientFromDb(@PathVariable Long mealId, @PathVariable Long id) {
        ingredientService.deleteById(id);
        return "redirect:/edit/meal/" + mealId;
    }
}
