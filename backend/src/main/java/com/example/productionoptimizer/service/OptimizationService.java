package com.example.productionoptimizer.service;

import com.example.productionoptimizer.dto.OptimizationResultDTO;
import com.example.productionoptimizer.dto.ProducedProductDTO;
import com.example.productionoptimizer.dto.RawMaterialDTO;
import com.example.productionoptimizer.entity.Product;
import com.example.productionoptimizer.entity.ProductComposition;
import com.example.productionoptimizer.entity.RawMaterial;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OptimizationService {

    private final ProductService productService;
    private final RawMaterialService rawMaterialService;

    public OptimizationService(ProductService productService, RawMaterialService rawMaterialService) {
        this.productService = productService;
        this.rawMaterialService = rawMaterialService;
    }

    public OptimizationResultDTO optimize() {
        // Get all products and raw materials
        List<Product> products = productService.findAllEntities();
        List<RawMaterial> rawMaterials = rawMaterialService.findAllEntities();

        if (products.isEmpty()) {
            return new OptimizationResultDTO(
                    new ArrayList<>(),
                    0.0,
                    rawMaterials.stream().map(this::toRawMaterialDTO).collect(Collectors.toList())
            );
        }

        // Create a map of raw material quantities (copy to avoid modifying original)
        Map<Long, Double> availableQuantities = new HashMap<>();
        for (RawMaterial rm : rawMaterials) {
            availableQuantities.put(rm.getId(), rm.getAvailableQuantity());
        }

        // Sort products by higher sales value (greedy approach)
        List<Product> sortedProducts = products.stream()
                .sorted(Comparator.comparingDouble(Product::getSalesValue).reversed())
                .collect(Collectors.toList());

        // List to track produced products
        List<ProducedProductDTO> producedProducts = new ArrayList<>();
        Double totalSalesValue = 0.0;

        // Produce products while there's enough raw material
        for (Product product : sortedProducts) {
            int quantityProduced = calculateMaxQuantity(product, availableQuantities);

            if (quantityProduced > 0) {
                // Deduct raw materials
                for (ProductComposition composition : product.getCompositions()) {
                    Long rawMaterialId = composition.getRawMaterial().getId();
                    Double needed = composition.getQuantityNeeded() * quantityProduced;
                    availableQuantities.put(rawMaterialId, availableQuantities.get(rawMaterialId) - needed);
                }

                // Add to produced products
                Double productSalesValue = product.getSalesValue() * quantityProduced;
                producedProducts.add(new ProducedProductDTO(
                        product.getId(),
                        product.getCode(),
                        product.getName(),
                        quantityProduced,
                        productSalesValue
                ));
                totalSalesValue += productSalesValue;
            }
        }

        // Build remaining raw materials list
        List<RawMaterialDTO> remainingRawMaterials = new ArrayList<>();
        for (RawMaterial rm : rawMaterials) {
            Double remaining = availableQuantities.get(rm.getId());
            remainingRawMaterials.add(new RawMaterialDTO(
                    rm.getId(),
                    rm.getCode(),
                    rm.getName(),
                    remaining != null ? remaining : 0.0,
                    rm.getUnit()
            ));
        }

        return new OptimizationResultDTO(producedProducts, totalSalesValue, remainingRawMaterials);
    }

    private int calculateMaxQuantity(Product product, Map<Long, Double> availableQuantities) {
        int maxQuantity = Integer.MAX_VALUE;

        for (ProductComposition composition : product.getCompositions()) {
            Long rawMaterialId = composition.getRawMaterial().getId();
            Double available = availableQuantities.getOrDefault(rawMaterialId, 0.0);
            Double needed = composition.getQuantityNeeded();

            if (needed <= 0) {
                continue;
            }

            int possibleQuantity = (int) Math.floor(available / needed);
            maxQuantity = Math.min(maxQuantity, possibleQuantity);
        }

        return maxQuantity == Integer.MAX_VALUE ? 0 : maxQuantity;
    }

    private RawMaterialDTO toRawMaterialDTO(RawMaterial entity) {
        return new RawMaterialDTO(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getAvailableQuantity(),
                entity.getUnit()
        );
    }
}
