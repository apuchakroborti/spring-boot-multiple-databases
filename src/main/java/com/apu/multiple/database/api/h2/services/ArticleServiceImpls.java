package com.apu.multiple.database.api.h2.services;


import com.apu.multiple.database.api.h2.dto.ArticleDto;
import com.apu.multiple.database.api.h2.models.Article;
import com.apu.multiple.database.api.h2.repository.ArticleRepository;
import com.apu.multiple.database.api.h2.util.ArticleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpls implements ArticleService {
//    @Autowired
//    private ArticleRepository articleRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    ArticleServiceImpls(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;

    }

    @Override
    public ArticleDto addArticle(ArticleDto articleDto) {
        Article article = ArticleUtils.dtoToEntityArticle(articleDto);
        article = articleRepository.save(article);
        return ArticleUtils.entityToDtoArticle(article);
    }

    @Override
    public List<ArticleDto> getArticles() {
        List<Article> articleList = articleRepository.findAll();
        return articleList.stream()
                .map(ArticleUtils::entityToDtoArticle)
                .collect(Collectors.toList());
    }
}
