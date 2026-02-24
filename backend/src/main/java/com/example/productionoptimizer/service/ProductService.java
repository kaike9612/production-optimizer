package com.example.productionoptimizer.service;

import com.example.productionoptimizer.dto.ProductCompositionDTO;
import com.example.productionoptimizer.dto.ProductDTO;
import com.example.productionoptimizer.entity.Product;
import com.example.productionoptimizer.entity.ProductComposition;
import com.example.productionoptimizer.entity.RawMaterial;
import com.example.productionoptimizer.exception.BadRequestException;
import com.example.productionoptimizer.exception.ResourceNotFoundException;
import com.example.productionoptimizer.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RawMaterialService rawMaterialService;

    public ProductService(ProductRepository productRepository, RawMaterialService rawMaterialService) {
        this.productRepository = productRepository;
        this.rawMaterialService = rawMaterialService;
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return toDTO(product);
    }

    public ProductDTO create(ProductDTO dto) {
        if (productRepository.existsByCode(dto.getCode())) {
            throw new BadRequestException("Product code already exists: " + dto.getCode());
        }

        if (dto.getCompositions() == null || dto.getCompositions().isEmpty()) {
            throw new BadRequestException("Product must have at least one composition");
        }

        for (ProductCompositionDTO compositionDTO : dto.getCompositions()) {
            if (compositionDTO.getQuantityNeeded() == null || compositionDTO.getQuantityNeeded() <= 0) {
                throw new BadRequestException("Composition quantity must be > 0");
            }
        }

        Product product = toEntity(dto);
        Product saved = productRepository.save(product);
        return toDTO(saved);
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!product.getCode().equals(dto.getCode()) && 
            productRepository.existsByCode(dto.getCode())) {
            throw new BadRequestException("Product code already exists: " + dto.getCode());
        }

        if (dto.getCompositions() == null || dto.getCompositions().isEmpty()) {
            throw new BadRequestException("Product must have at least one composition");
        }

        for (ProductCompositionDTO compositionDTO : dto.getCompositions()) {
            if (compositionDTO.getQuantityNeeded() == null || compositionDTO.getQuantityNeeded() <= 0) {
                throw new BadRequestException("Composition quantity must be > 0");
            }
        }

        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setSalesValue(dto.getSalesValue());
        
        product.clearCompositions();
        for (ProductCompositionDTO compositionDTO : dto.getCompositions()) {
            RawMaterial rawMaterial = rawMaterialService.findEntityById(compositionDTO.getRawMaterialId());
            product.addComposition(rawMaterial, compositionDTO.getQuantityNeeded());
        }

        Product updated = productRepository.save(product);
        return toDTO(updated);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public Product findEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public List<Product> findAllEntities() {
        return productRepository.findAll();
    }

    private ProductDTO toDTO(Product entity) {
        List<ProductCompositionDTO> compositions = entity.getCompositions().stream()
                .map(c -> new ProductCompositionDTO(
                        c.getId(),
                        c.getRawMaterial().getId(),
                        c.getRawMaterial().getCode(),
                        c.getRawMaterial().getName(),
                        c.getQuantityNeeded()
                ))
                .collect(Collectors.toList());

        return new ProductDTO(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getSalesValue(),
                compositions
        );
    }

    private Product toEntity(ProductDTO dto) {
        Product product = new Product(dto.getCode(), dto.getName(), dto.getSalesValue());
        
        if (dto.getCompositions() != null) {
            for (ProductCompositionDTO compositionDTO : dto.getCompositions()) {
                RawMaterial rawMaterial = rawMaterialService.findEntityById(compositionDTO.getRawMaterialId());
                product.addComposition(rawMaterial, compositionDTO.getQuantityNeeded());
            }
        }
        
        return product;
    }
}
