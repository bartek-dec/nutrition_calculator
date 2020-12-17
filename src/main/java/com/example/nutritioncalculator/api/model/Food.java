package com.example.nutritioncalculator.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "foodId",
        "label",
        "nutrients",
        "category",
        "categoryLabel",
        "image"
})
public class Food {

    @JsonProperty("foodId")
    private String foodId;
    @JsonProperty("label")
    private String label;
    @JsonProperty("nutrients")
    private Nutrients nutrients;
    @JsonProperty("category")
    private String category;
    @JsonProperty("categoryLabel")
    private String categoryLabel;
    @JsonProperty("image")
    private String image;

    @JsonProperty("foodId")
    public String getFoodId() {
        return foodId;
    }

    @JsonProperty("foodId")
    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("nutrients")
    public Nutrients getNutrients() {
        return nutrients;
    }

    @JsonProperty("nutrients")
    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("categoryLabel")
    public String getCategoryLabel() {
        return categoryLabel;
    }

    @JsonProperty("categoryLabel")
    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }
}
