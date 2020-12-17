package com.example.nutritioncalculator.api.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "uri",
        "label",
        "weight",
        "qualified"
})
public class Measure {

    @JsonProperty("uri")
    private String uri;
    @JsonProperty("label")
    private String label;
    @JsonProperty("weight")
    private Double weight;
    @JsonProperty("qualified")
    private List<Qualified> qualified = null;

    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("weight")
    public Double getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @JsonProperty("qualified")
    public List<Qualified> getQualified() {
        return qualified;
    }

    @JsonProperty("qualified")
    public void setQualified(List<Qualified> qualified) {
        this.qualified = qualified;
    }
}
