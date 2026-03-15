package com.class2000.reunion.controller;

import com.class2000.reunion.model.Photo;
import com.class2000.reunion.repository.PhotoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoRepository photoRepository;

    // All photos (general gallery — no get-together)
    @GetMapping
    public List<Photo> getAll() {
        return photoRepository.findByGetTogetherIdIsNullOrderByUploadedAtDesc();
    }

    // Photos for a specific get-together
    @GetMapping("/get-together/{getTogetherId}")
    public List<Photo> getByGetTogether(@PathVariable Long getTogetherId) {
        return photoRepository.findByGetTogetherIdOrderByUploadedAtAsc(getTogetherId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Photo> getById(@PathVariable Long id) {
        return photoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Photo> create(@Valid @RequestBody Photo photo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(photoRepository.save(photo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!photoRepository.existsById(id)) return ResponseEntity.notFound().build();
        photoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
