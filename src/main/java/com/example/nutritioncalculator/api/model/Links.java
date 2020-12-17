package com.example.nutritioncalculator.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "next"
})
public class Links {

    @JsonProperty("next")
    private Next next;

    @JsonProperty("next")
    public Next getNext() {
        return next;
    }

    @JsonProperty("next")
    public void setNext(Next next) {
        this.next = next;
    }
}
