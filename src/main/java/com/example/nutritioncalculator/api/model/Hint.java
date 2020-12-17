package com.example.nutritioncalculator.api.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "food",
        "measures"
})
public class Hint {

    @JsonProperty("food")
    private FoodHint food;
    @JsonProperty("measures")
    private List<Measure> measures = null;

    @JsonProperty("food")
    public FoodHint getFood() {
        return food;
    }

    @JsonProperty("food")
    public void setFood(FoodHint food) {
        this.food = food;
    }

    @JsonProperty("measures")
    public List<Measure> getMeasures() {
        return measures;
    }

    @JsonProperty("measures")
    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }
}
