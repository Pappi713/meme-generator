package com.example.meme.repository;

import com.example.meme.model.Reaction;
import org.springframework.data.repository.CrudRepository;

public interface ReactionRepository extends CrudRepository<Reaction, Long> {
}
