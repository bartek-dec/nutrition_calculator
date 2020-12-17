package com.example.nutritioncalculator.controller;

import com.example.nutritioncalculator.api.service.ClientService;
import com.example.nutritioncalculator.domain.Ingredient;
import com.example.nutritioncalculator.dto.UserRequest;
import com.example.nutritioncalculator.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller enables to get user input and displays the response
 * from api provider.
 *
 * @author Bartek Dec
 */
@Controller
public class AppController {

    private ClientService clientService;
    private MealService mealService;
    private List<Ingredient> apiIngredients;

    @Autowired
    public AppController(ClientService clientService,
                         MealService mealService) {
        this.clientService = clientService;
        this.mealService = mealService;
        this.apiIngredients = new ArrayList<>();
    }

    /**
     * This method displays form for the user, in order to provide name and weight of the
     * interested food.
     *
     * @param model Holder for model attributes.
     * @return Name of the html view containing form for user input.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "index";
    }

    /**
     * This method validates provided inputs, sends request to the external database and
     * displays table with received ingredients.
     *
     * @param userRequest Request object from Http protocol.
     * @param result         BindingResult validates request object and add any errors.
     * @param model          Holder for model attributes.
     * @return Name of the html view containing table filled with found products, otherwise
     * message informing about bad request.
     */
    @PostMapping("/search")
    public String searchFood(@Validated UserRequest userRequest, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "index";
        }

        try {
            int weight = Integer.parseInt(userRequest.getWeight());

            if (weight <= 0) {
                model.addAttribute("error", "Weight must be an integer greater than zero");
                return "index";
            }

            apiIngredients = clientService.getIngredients(userRequest.getProduct(), weight);
            model.addAttribute("foundIngredients", apiIngredients);
            return "index";
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Weight must be an integer greater than zero");
            return "index";
        }
    }

    /**
     * This method sends selected food to the service layer and displays empty form ready to receive
     * another user input.
     *
     * @param id Identification string assigned to the ingredient by external database.
     * @return Name of the end point which displays empty form.
     */
    @GetMapping("/add/{id}")
    public String addIngredient(@PathVariable String id) {
        Ingredient ingredient = apiIngredients.stream()
                .filter(e -> Objects.equals(e.getFoodId(), id))
                .findFirst().get();

        mealService.addIngredient(ingredient);
        apiIngredients.clear();
        return "redirect:/";
    }
}
