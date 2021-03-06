package com.gameon.mycash_carteiradigital.model;

import java.io.Serializable;

public class Output implements Serializable {

    private Long idOutput;
    private String dateOutput;
    private Double valueOutput;
    private String descriptionOutput;
    private Long idCategory;
    private String typeOutput;

    public Output(){

    }

    public Output(Long idOutput, String dateOutput, Double valueOutput, String descriptionOutput, Long idCategory, String typeOutput) {
        this.idOutput = idOutput;
        this.dateOutput = dateOutput;
        this.valueOutput = valueOutput;
        this.descriptionOutput = descriptionOutput;
        this.idCategory = idCategory;
        this.typeOutput = typeOutput;
    }

    public Long getIdOutput() {
        return idOutput;
    }

    public void setIdOutput(Long idOutput) {
        this.idOutput = idOutput;
    }

    public String getDateOutput() {
        return dateOutput;
    }

    public void setDateOutput(String dateOutput) {
        this.dateOutput = dateOutput;
    }

    public Double getValueOutput() {
        return valueOutput;
    }

    public String getTypeOutput()  { return typeOutput;}

    public void setTypeOutput(String typeOutput) { this.typeOutput = typeOutput;}

    public void setValueOutput(Double valueOutput) {
        this.valueOutput = valueOutput;
    }

    public String getDescriptionOutput() {
        return descriptionOutput;
    }

    public void setDescriptionOutput(String descriptionOutput) {
        this.descriptionOutput = descriptionOutput;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }
}
