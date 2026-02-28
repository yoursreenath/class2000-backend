package com.class2000.reunion.controller;

import com.class2000.reunion.model.News;
import com.class2000.reunion.repository.NewsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsRepository newsRepository;

    @GetMapping
    public List<News> getAll() {
        return newsRepository.findAllByOrderByPublishedAtDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getById(@PathVariable Long id) {
        return newsRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<News> create(@Valid @RequestBody News news) {
        return ResponseEntity.status(HttpStatus.CREATED).body(newsRepository.save(news));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!newsRepository.existsById(id)) return ResponseEntity.notFound().build();
        newsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
