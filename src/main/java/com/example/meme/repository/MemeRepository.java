package com.example.meme.repository;

import com.example.meme.model.Meme;
import org.springframework.data.repository.CrudRepository;

public interface MemeRepository extends CrudRepository<Meme, Long> {
}
