package com.apu.multiple.database.api.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apu.multiple.database.api.model.book.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

}
