package com.example.productionoptimizer.dto;

import java.util.List;

public class OptimizationResultDTO {

    private List<ProducedProductDTO> producedProducts;
    private Double totalSalesValue;
    private List<RawMaterialDTO> remainingRawMaterials;

    public OptimizationResultDTO() {
    }

    public OptimizationResultDTO(List<ProducedProductDTO> producedProducts, 
                                  Double totalSalesValue, 
                                  List<RawMaterialDTO> remainingRawMaterials) {
        this.producedProducts = producedProducts;
        this.totalSalesValue = totalSalesValue;
        this.remainingRawMaterials = remainingRawMaterials;
    }

    public List<ProducedProductDTO> getProducedProducts() {
        return producedProducts;
    }

    public void setProducedProducts(List<ProducedProductDTO> producedProducts) {
        this.producedProducts = producedProducts;
    }

    public Double getTotalSalesValue() {
        return totalSalesValue;
    }

    public void setTotalSalesValue(Double totalSalesValue) {
        this.totalSalesValue = totalSalesValue;
    }

    public List<RawMaterialDTO> getRemainingRawMaterials() {
        return remainingRawMaterials;
    }

    public void setRemainingRawMaterials(List<RawMaterialDTO> remainingRawMaterials) {
        this.remainingRawMaterials = remainingRawMaterials;
    }
}
