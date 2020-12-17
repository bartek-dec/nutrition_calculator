package com.example.nutritioncalculator.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ENERC_KCAL",
        "PROCNT",
        "FAT",
        "CHOCDF",
        "FIBTG"
})
public class Nutrients {

    @JsonProperty("ENERC_KCAL")
    private Double eNERCKCAL;
    @JsonProperty("PROCNT")
    private Double pROCNT;
    @JsonProperty("FAT")
    private Double fAT;
    @JsonProperty("CHOCDF")
    private Double cHOCDF;
    @JsonProperty("FIBTG")
    private Double fIBTG;

    @JsonProperty("ENERC_KCAL")
    public Double getENERCKCAL() {
        return eNERCKCAL;
    }

    @JsonProperty("ENERC_KCAL")
    public void setENERCKCAL(Double eNERCKCAL) {
        this.eNERCKCAL = eNERCKCAL;
    }

    @JsonProperty("PROCNT")
    public Double getPROCNT() {
        return pROCNT;
    }

    @JsonProperty("PROCNT")
    public void setPROCNT(Double pROCNT) {
        this.pROCNT = pROCNT;
    }

    @JsonProperty("FAT")
    public Double getFAT() {
        return fAT;
    }

    @JsonProperty("FAT")
    public void setFAT(Double fAT) {
        this.fAT = fAT;
    }

    @JsonProperty("CHOCDF")
    public Double getCHOCDF() {
        return cHOCDF;
    }

    @JsonProperty("CHOCDF")
    public void setCHOCDF(Double cHOCDF) {
        this.cHOCDF = cHOCDF;
    }

    @JsonProperty("FIBTG")
    public Double getFIBTG() {
        return fIBTG;
    }

    @JsonProperty("FIBTG")
    public void setFIBTG(Double fIBTG) {
        this.fIBTG = fIBTG;
    }
}
