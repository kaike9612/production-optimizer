package com.example.productionoptimizer.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(name = "sales_value", nullable = false)
    private Double salesValue;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProductComposition> compositions = new ArrayList<>();

    public Product() {
    }

    public Product(String code, String name, Double salesValue) {
        this.code = code;
        this.name = name;
        this.salesValue = salesValue;
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

    public List<ProductComposition> getCompositions() {
        return compositions;
    }

    public void setCompositions(List<ProductComposition> compositions) {
        this.compositions = compositions;
    }

    public void addComposition(RawMaterial rawMaterial, Double quantityNeeded) {
        ProductComposition composition = new ProductComposition(this, rawMaterial, quantityNeeded);
        compositions.add(composition);
    }

    public void clearCompositions() {
        compositions.clear();
    }
}
