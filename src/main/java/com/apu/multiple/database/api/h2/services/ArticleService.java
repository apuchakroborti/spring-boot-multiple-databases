package com.apu.multiple.database.api.h2.services;

import com.apu.multiple.database.api.h2.dto.ArticleDto;

import java.util.List;

public interface ArticleService {
    ArticleDto addArticle(ArticleDto articleDto);
    List<ArticleDto> getArticles();
}
