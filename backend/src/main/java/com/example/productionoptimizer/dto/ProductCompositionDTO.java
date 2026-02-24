package com.example.productionoptimizer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductCompositionDTO {

    private Long id;

    @NotNull(message = "Raw material ID is required")
    private Long rawMaterialId;

    private String rawMaterialCode;
    private String rawMaterialName;

    @NotNull(message = "Quantity needed is required")
    @Positive(message = "Quantity needed must be > 0")
    private Double quantityNeeded;

    public ProductCompositionDTO() {
    }

    public ProductCompositionDTO(Long id, Long rawMaterialId, String rawMaterialCode, 
                                  String rawMaterialName, Double quantityNeeded) {
        this.id = id;
        this.rawMaterialId = rawMaterialId;
        this.rawMaterialCode = rawMaterialCode;
        this.rawMaterialName = rawMaterialName;
        this.quantityNeeded = quantityNeeded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public String getRawMaterialCode() {
        return rawMaterialCode;
    }

    public void setRawMaterialCode(String rawMaterialCode) {
        this.rawMaterialCode = rawMaterialCode;
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    public Double getQuantityNeeded() {
        return quantityNeeded;
    }

    public void setQuantityNeeded(Double quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
    }
}
