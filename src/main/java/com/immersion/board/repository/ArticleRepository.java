package com.immersion.board.repository;

import com.immersion.board.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
