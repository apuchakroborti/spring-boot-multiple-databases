package com.apu.multiple.database.api.h2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ARTICLE_TABLE")
public class Article {
    @Id
    private Integer id;
    private String description;
}
