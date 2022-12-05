package com.apu.multiple.database.api.postgres.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto implements Serializable {
    private int id;
    private String name;
}
