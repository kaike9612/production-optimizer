package com.example.productionoptimizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class RawMaterialDTO {

    private Long id;

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Available quantity is required")
    @PositiveOrZero(message = "Available quantity must be >= 0")
    private Double availableQuantity;

    @NotBlank(message = "Unit is required")
    private String unit;

    public RawMaterialDTO() {
    }

    public RawMaterialDTO(Long id, String code, String name, Double availableQuantity, String unit) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Double availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
