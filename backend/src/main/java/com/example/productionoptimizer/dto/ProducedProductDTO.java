package com.example.productionoptimizer.dto;

public class ProducedProductDTO {

    private Long productId;
    private String productCode;
    private String productName;
    private Integer quantityProduced;
    private Double salesValue;

    public ProducedProductDTO() {
    }

    public ProducedProductDTO(Long productId, String productCode, String productName, 
                               Integer quantityProduced, Double salesValue) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.quantityProduced = quantityProduced;
        this.salesValue = salesValue;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantityProduced() {
        return quantityProduced;
    }

    public void setQuantityProduced(Integer quantityProduced) {
        this.quantityProduced = quantityProduced;
    }

    public Double getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Double salesValue) {
        this.salesValue = salesValue;
    }
}
