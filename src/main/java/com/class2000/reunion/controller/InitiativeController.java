package com.class2000.reunion.controller;

import com.class2000.reunion.model.Initiative;
import com.class2000.reunion.repository.InitiativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/initiatives")
@RequiredArgsConstructor
public class InitiativeController {

    private final InitiativeRepository initiativeRepo;

    @GetMapping
    public List<Initiative> getAll() {
        return initiativeRepo.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Initiative> getById(@PathVariable Long id) {
        return initiativeRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Initiative create(@RequestBody Initiative initiative) {
        return initiativeRepo.save(initiative);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Initiative> update(@PathVariable Long id, @RequestBody Initiative updated) {
        return initiativeRepo.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setType(updated.getType());
            existing.setBadge(updated.getBadge());
            existing.setImageUrl(updated.getImageUrl());
            existing.setYear(updated.getYear());
            return ResponseEntity.ok(initiativeRepo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!initiativeRepo.existsById(id)) return ResponseEntity.notFound().build();
        initiativeRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
