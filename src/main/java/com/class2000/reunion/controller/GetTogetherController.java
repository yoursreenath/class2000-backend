package com.class2000.reunion.controller;

import com.class2000.reunion.model.GetTogether;
import com.class2000.reunion.repository.GetTogetherRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/get-togethers")
@RequiredArgsConstructor
public class GetTogetherController {

    private final GetTogetherRepository repo;

    @GetMapping
    public List<GetTogether> getAll() {
        return repo.findAllByOrderByEventYearAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTogether> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GetTogether> create(@Valid @RequestBody GetTogether gt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(gt));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetTogether> update(@PathVariable Long id, @RequestBody GetTogether updated) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setLocation(updated.getLocation());
            existing.setEventYear(updated.getEventYear());
            existing.setCoverImageUrl(updated.getCoverImageUrl());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
