package com.example.productionoptimizer.controller;

import com.example.productionoptimizer.dto.OptimizationResultDTO;
import com.example.productionoptimizer.service.OptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/optimization")
public class OptimizationController {

    private final OptimizationService optimizationService;

    public OptimizationController(OptimizationService optimizationService) {
        this.optimizationService = optimizationService;
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizationResultDTO> optimize() {
        return ResponseEntity.ok(optimizationService.optimize());
    }
}
