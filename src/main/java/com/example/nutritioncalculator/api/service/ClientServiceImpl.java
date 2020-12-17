package com.example.nutritioncalculator.api.service;

import com.example.nutritioncalculator.api.model.FoodApi;
import com.example.nutritioncalculator.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service communicates between client and external api using Http protocol.
 *
 * @author Bartek Dec
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Value("${base_url}")
    private String baseUrl;
    @Value("${app_id_param}")
    private String appIdParam;
    @Value("${app_id}")
    private String appId;
    @Value("${app_key_param}")
    private String appKeyParam;
    @Value("${app_key}")
    private String appKey;
    @Value("${ingr_param}")
    private String ingr;

    private RestTemplate restTemplate;


    @Autowired
    public ClientServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * This method sends ingredient name to the external api using Http protocol
     * and provides list of the received ingredients to the corresponding controller.
     *
     * @param ingredient Name of the ingredient defined by the user.
     * @param weight     Weight of the ingredient in question, defined by the user.
     * @return List of the received ingredients.
     */
    @Override
    public List<Ingredient> getIngredients(String ingredient, int weight) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam(appIdParam, appId)
                .queryParam(appKeyParam, appKey)
                .queryParam(ingr, ingredient);

        String uriString = uri.toUriString();

        try {
            FoodApi foodApi = restTemplate.getForObject(uriString, FoodApi.class);
            return mapIngredients(foodApi, weight);
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    /**
     * This method converts the Java representation of the api response to the list
     * of ingredients.
     *
     * @param foodApi Name of the ingredient defined by the user.
     * @param weight  Weight of the ingredient defined by the user.
     * @return List of the received ingredients.
     */
    private List<Ingredient> mapIngredients(FoodApi foodApi, int weight) {

        return foodApi.getHints().stream()
                .map(element -> {
                    Ingredient ingredient = new Ingredient();

                    ingredient.setFoodId(element.getFood().getFoodId());
                    ingredient.setImageUrl(element.getFood().getImage());
                    ingredient.setQuantity(weight);
                    ingredient.setUnit("g");
                    ingredient.setName(element.getFood().getLabel());

                    if (element.getFood().getNutrients().getENERCKCAL() != null) {
                        ingredient.setEnergy(calculateNutrients(element.getFood().getNutrients().getENERCKCAL(), weight));
                    } else {
                        ingredient.setEnergy(0);
                    }

                    if (element.getFood().getNutrients().getPROCNT() != null) {
                        ingredient.setProtein(calculateNutrients(element.getFood().getNutrients().getPROCNT(), weight));
                    } else {
                        ingredient.setProtein(0);
                    }

                    if (element.getFood().getNutrients().getFAT() != null) {
                        ingredient.setFat(calculateNutrients(element.getFood().getNutrients().getFAT(), weight));
                    } else {
                        ingredient.setFat(0);
                    }

                    if (element.getFood().getNutrients().getCHOCDF() != null) {
                        ingredient.setCarbs(calculateNutrients(element.getFood().getNutrients().getCHOCDF(), weight));
                    } else {
                        ingredient.setCarbs(0);
                    }

                    return ingredient;
                }).collect(Collectors.toList());
    }

    /**
     * This method scales the value of each nutrition base on the current weight.
     *
     * @param nutrients Nutrition vale per 100 g.
     * @param weight    Weight of the ingredient defined by the user.
     * @return Integer value of the scaled nutrition value.
     */
    private int calculateNutrients(Double nutrients, int weight) {
        double result = (weight / 100.0) * nutrients;
        return (int) result;
    }
}
