package com.example.productionoptimizer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Sales value is required")
    @PositiveOrZero(message = "Sales value must be >= 0")
    private Double salesValue;

    @Valid
    private List<ProductCompositionDTO> compositions;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String code, String name, Double salesValue, 
                      List<ProductCompositionDTO> compositions) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.salesValue = salesValue;
        this.compositions = compositions;
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

    public Double getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Double salesValue) {
        this.salesValue = salesValue;
    }

    public List<ProductCompositionDTO> getCompositions() {
        return compositions;
    }

    public void setCompositions(List<ProductCompositionDTO> compositions) {
        this.compositions = compositions;
    }
}
