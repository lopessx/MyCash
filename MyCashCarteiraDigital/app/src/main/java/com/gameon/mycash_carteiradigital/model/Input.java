package com.gameon.mycash_carteiradigital.model;

import java.io.Serializable;

public class Input implements Serializable {

    private Long idInput;
    private String dateInput;
    private Double valueInput;
    private String descriptionInput;
    private Long idCategory;
    private String typeInput;

    public Input(){}

    public Input(Long idInput, String dateInput, Double valueInput, String descriptionInput, Long idCategory, String typeInput) {
        this.idInput = idInput;
        this.dateInput = dateInput;
        this.valueInput = valueInput;
        this.descriptionInput = descriptionInput;
        this.idCategory = idCategory;
        this.typeInput = typeInput;
    }

    public Long getIdInput() {
        return idInput;
    }

    public void setIdInput(Long idInput) {
        this.idInput = idInput;
    }

    public String getDateInput() {
        return dateInput;
    }

    public void setDateInput(String dateInput) {
        this.dateInput = dateInput;
    }

    public Double getValueInput() {
        return valueInput;
    }

    public void setValueInput(Double valueInput) {
        this.valueInput = valueInput;
    }

    public String getDescriptionInput() {
        return descriptionInput;
    }

    public void setDescriptionInput(String descriptionInput) {
        this.descriptionInput = descriptionInput;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getTypeInput() {
        return typeInput;
    }

    public void setTypeInput(String typeInput) {
        this.typeInput = typeInput;
    }
}
