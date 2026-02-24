package com.example.productionoptimizer.controller;

import com.example.productionoptimizer.dto.RawMaterialDTO;
import com.example.productionoptimizer.service.RawMaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialDTO>> findAll() {
        return ResponseEntity.ok(rawMaterialService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(rawMaterialService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RawMaterialDTO> create(@Valid @RequestBody RawMaterialDTO dto) {
        RawMaterialDTO created = rawMaterialService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialDTO> update(@PathVariable Long id, @Valid @RequestBody RawMaterialDTO dto) {
        return ResponseEntity.ok(rawMaterialService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
