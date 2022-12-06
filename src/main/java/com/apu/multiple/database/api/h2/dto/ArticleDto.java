package com.apu.multiple.database.api.h2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Integer id;
    private String description;
}
