package com.apu.multiple.database.api.h2.repository;

import com.apu.multiple.database.api.h2.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
