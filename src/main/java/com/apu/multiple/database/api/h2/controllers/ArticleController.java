package com.apu.multiple.database.api.h2.controllers;

import com.apu.multiple.database.api.h2.dto.ArticleDto;
import com.apu.multiple.database.api.h2.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    /*@Autowired
    private ArticleService articleService;*/
    private final  ArticleService articleService;

    @Autowired
    ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @PostMapping
    public ArticleDto addArticle(@RequestBody ArticleDto articleDto){
        return articleService.addArticle(articleDto);
    }

    @GetMapping
    public List<ArticleDto> getArticles(){
        return articleService.getArticles();
    }

}
