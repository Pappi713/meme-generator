package com.example.meme.repository;

import com.example.meme.model.Meme;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemeRepository extends PagingAndSortingRepository<Meme, Long> {

  List<Meme> findAllByIsOnFeed(Boolean isOnFeed, Pageable pageable);
  List<Meme> findAllByIsOnFeed(Boolean isOnFeed);
  List<Meme> findAll();
}
