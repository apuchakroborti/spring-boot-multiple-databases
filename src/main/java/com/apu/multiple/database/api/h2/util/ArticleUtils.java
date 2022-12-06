package com.apu.multiple.database.api.h2.util;


import com.apu.multiple.database.api.h2.dto.ArticleDto;
import com.apu.multiple.database.api.h2.models.Article;

public class ArticleUtils {
    public static Article dtoToEntityArticle(ArticleDto articleDto){
        return new Article(articleDto.getId(), articleDto.getDescription());
    }
    public static ArticleDto entityToDtoArticle(Article article){
        return new ArticleDto(article.getId(), article.getDescription());
    }
}
