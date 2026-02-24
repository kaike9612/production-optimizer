package com.example.productionoptimizer.service;

import com.example.productionoptimizer.dto.RawMaterialDTO;
import com.example.productionoptimizer.entity.RawMaterial;
import com.example.productionoptimizer.exception.BadRequestException;
import com.example.productionoptimizer.exception.ResourceNotFoundException;
import com.example.productionoptimizer.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public List<RawMaterialDTO> findAll() {
        return rawMaterialRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<RawMaterial> findAllEntities() {
        return rawMaterialRepository.findAll();
    }

    public RawMaterialDTO findById(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found with id: " + id));
        return toDTO(rawMaterial);
    }

    public RawMaterialDTO create(RawMaterialDTO dto) {
        if (rawMaterialRepository.existsByCode(dto.getCode())) {
            throw new BadRequestException("Raw material code already exists: " + dto.getCode());
        }
        RawMaterial rawMaterial = toEntity(dto);
        RawMaterial saved = rawMaterialRepository.save(rawMaterial);
        return toDTO(saved);
    }

    public RawMaterialDTO update(Long id, RawMaterialDTO dto) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found with id: " + id));

        if (!rawMaterial.getCode().equals(dto.getCode()) && 
            rawMaterialRepository.existsByCode(dto.getCode())) {
            throw new BadRequestException("Raw material code already exists: " + dto.getCode());
        }

        rawMaterial.setCode(dto.getCode());
        rawMaterial.setName(dto.getName());
        rawMaterial.setAvailableQuantity(dto.getAvailableQuantity());
        rawMaterial.setUnit(dto.getUnit());

        RawMaterial updated = rawMaterialRepository.save(rawMaterial);
        return toDTO(updated);
    }

    public void delete(Long id) {
        if (!rawMaterialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Raw material not found with id: " + id);
        }
        rawMaterialRepository.deleteById(id);
    }

    public RawMaterial findEntityById(Long id) {
        return rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found with id: " + id));
    }

    private RawMaterialDTO toDTO(RawMaterial entity) {
        return new RawMaterialDTO(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getAvailableQuantity(),
                entity.getUnit()
        );
    }

    private RawMaterial toEntity(RawMaterialDTO dto) {
        return new RawMaterial(
                dto.getCode(),
                dto.getName(),
                dto.getAvailableQuantity(),
                dto.getUnit()
        );
    }
}
