package com.apu.multiple.database.api.postgres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apu.multiple.database.api.postgres.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

}
