package com.example.productionoptimizer.service;

import com.example.productionoptimizer.dto.OptimizationResultDTO;
import com.example.productionoptimizer.dto.ProducedProductDTO;
import com.example.productionoptimizer.entity.Product;
import com.example.productionoptimizer.entity.ProductComposition;
import com.example.productionoptimizer.entity.RawMaterial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OptimizationServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private RawMaterialService rawMaterialService;

    @InjectMocks
    private OptimizationService optimizationService;

    private RawMaterial rawMaterial1;
    private RawMaterial rawMaterial2;

    @BeforeEach
    void setUp() {
        rawMaterial1 = new RawMaterial("RM001", "Steel", 100.0, "kg");
        rawMaterial1.setId(1L);

        rawMaterial2 = new RawMaterial("RM002", "Plastic", 50.0, "kg");
        rawMaterial2.setId(2L);
    }

    @Test
    void testSimpleCaseWithOneProduct() {
        // Setup: One product with one raw material
        Product product = new Product("P001", "Widget", 100.0);
        product.setId(1L);
        ProductComposition composition = new ProductComposition(product, rawMaterial1, 10.0);
        product.getCompositions().add(composition);

        when(productService.findAllEntities()).thenReturn(Arrays.asList(product));
        when(rawMaterialService.findAllEntities()).thenReturn(Arrays.asList(rawMaterial1));

        OptimizationResultDTO result = optimizationService.optimize();

        assertNotNull(result);
        assertEquals(1, result.getProducedProducts().size());
        
        ProducedProductDTO produced = result.getProducedProducts().get(0);
        assertEquals("P001", produced.getProductCode());
        assertEquals(10, produced.getQuantityProduced()); // 100kg / 10kg = 10 units
        assertEquals(1000.0, produced.getSalesValue());
        
        assertEquals(1000.0, result.getTotalSalesValue());
        assertEquals(0.0, result.getRemainingRawMaterials().get(0).getAvailableQuantity());
    }

    @Test
    void testConflictBetweenTwoProducts() {
        // Setup: Two products competing for the same raw material
        rawMaterial1.setAvailableQuantity(50.0);

        Product product1 = new Product("P001", "HighValueProduct", 200.0);
        product1.setId(1L);
        ProductComposition comp1 = new ProductComposition(product1, rawMaterial1, 10.0);
        product1.getCompositions().add(comp1);

        Product product2 = new Product("P002", "LowValueProduct", 50.0);
        product2.setId(2L);
        ProductComposition comp2 = new ProductComposition(product2, rawMaterial1, 5.0);
        product2.getCompositions().add(comp2);

        when(productService.findAllEntities()).thenReturn(Arrays.asList(product1, product2));
        when(rawMaterialService.findAllEntities()).thenReturn(Arrays.asList(rawMaterial1));

        OptimizationResultDTO result = optimizationService.optimize();

        assertNotNull(result);
        // High value product should be produced first
        assertTrue(result.getProducedProducts().size() >= 1);
        
        // First product should be the high value one (200.0 > 50.0)
        ProducedProductDTO firstProduced = result.getProducedProducts().get(0);
        assertEquals("P001", firstProduced.getProductCode());
        assertEquals(5, firstProduced.getQuantityProduced()); // 50kg / 10kg = 5 units
        
        // Total sales value: 5 * 200 = 1000
        assertEquals(1000.0, result.getTotalSalesValue());
    }

    @Test
    void testProductWithMultipleRawMaterials() {
        // Setup: One product with multiple raw materials
        Product product = new Product("P001", "ComplexProduct", 300.0);
        product.setId(1L);
        
        ProductComposition comp1 = new ProductComposition(product, rawMaterial1, 20.0); // 100kg / 20 = 5
        ProductComposition comp2 = new ProductComposition(product, rawMaterial2, 10.0); // 50kg / 10 = 5
        product.getCompositions().add(comp1);
        product.getCompositions().add(comp2);

        when(productService.findAllEntities()).thenReturn(Arrays.asList(product));
        when(rawMaterialService.findAllEntities()).thenReturn(Arrays.asList(rawMaterial1, rawMaterial2));

        OptimizationResultDTO result = optimizationService.optimize();

        assertNotNull(result);
        assertEquals(1, result.getProducedProducts().size());
        
        ProducedProductDTO produced = result.getProducedProducts().get(0);
        assertEquals(5, produced.getQuantityProduced()); // limited by both raw materials
        assertEquals(1500.0, produced.getSalesValue());
        assertEquals(1500.0, result.getTotalSalesValue());
    }

    @Test
    void testCaseWhereNothingCanBeProduced() {
        // Setup: Raw material quantity is 0
        rawMaterial1.setAvailableQuantity(0.0);
        rawMaterial2.setAvailableQuantity(0.0);

        Product product = new Product("P001", "Product", 100.0);
        product.setId(1L);
        ProductComposition comp = new ProductComposition(product, rawMaterial1, 10.0);
        product.getCompositions().add(comp);

        when(productService.findAllEntities()).thenReturn(Arrays.asList(product));
        when(rawMaterialService.findAllEntities()).thenReturn(Arrays.asList(rawMaterial1, rawMaterial2));

        OptimizationResultDTO result = optimizationService.optimize();

        assertNotNull(result);
        assertTrue(result.getProducedProducts().isEmpty());
        assertEquals(0.0, result.getTotalSalesValue());
        
        // Check that raw materials remain at 0
        assertEquals(0.0, result.getRemainingRawMaterials().get(0).getAvailableQuantity());
    }

    @Test
    void testEmptyProductsList() {
        when(productService.findAllEntities()).thenReturn(Collections.emptyList());
        when(rawMaterialService.findAllEntities()).thenReturn(Arrays.asList(rawMaterial1));

        OptimizationResultDTO result = optimizationService.optimize();

        assertNotNull(result);
        assertTrue(result.getProducedProducts().isEmpty());
        assertEquals(0.0, result.getTotalSalesValue());
        assertEquals(1, result.getRemainingRawMaterials().size());
    }
}
