package com.example.nutritioncalculator.dto;

import javax.validation.constraints.NotBlank;

/**
 * This is UserRequest to transfer data between web layer and service layer.
 *
 * @author Bartek Dec
 */
public class UserRequest {

    @NotBlank(message = "Ingredient can not be empty")
    private String product;

    @NotBlank(message = "Weight must be an integer greater than zero")
    private String weight;

    private String foodId;
    private Long foodDbId;
    private Long mealDbId;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public Long getFoodDbId() {
        return foodDbId;
    }

    public void setFoodDbId(Long foodDbId) {
        this.foodDbId = foodDbId;
    }

    public Long getMealDbId() {
        return mealDbId;
    }

    public void setMealDbId(Long mealDbId) {
        this.mealDbId = mealDbId;
    }
}
