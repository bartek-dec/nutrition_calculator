package com.example.nutritioncalculator.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "qualifiers",
        "weight"
})
public class Qualified {

    @JsonProperty("qualifiers")
    private List<Size> qualifiers = null;
    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("qualifiers")
    public List<Size> getQualifiers() {
        return qualifiers;
    }

    @JsonProperty("qualifiers")
    public void setQualifiers(List<Size> qualifiers) {
        this.qualifiers = qualifiers;
    }

    @JsonProperty("weight")
    public Double getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
