package com.example.nutritioncalculator.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "food"
})
public class Parsed {

    @JsonProperty("food")
    private Food food;

    @JsonProperty("food")
    public Food getFood() {
        return food;
    }

    @JsonProperty("food")
    public void setFood(Food food) {
        this.food = food;
    }
}
