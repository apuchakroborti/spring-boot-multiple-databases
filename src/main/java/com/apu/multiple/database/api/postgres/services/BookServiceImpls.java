package com.apu.multiple.database.api.postgres.services;

import com.apu.multiple.database.api.mysql.entity.User;
import com.apu.multiple.database.api.mysql.util.UserUtils;
import com.apu.multiple.database.api.postgres.dto.BookDto;
import com.apu.multiple.database.api.postgres.entity.Book;
import com.apu.multiple.database.api.postgres.repository.BookRepository;
import com.apu.multiple.database.api.postgres.util.BookUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpls implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = BookUtils.dtoToEntityBook(bookDto);
        book = bookRepository.save(book);
        return BookUtils.entityToDtoBookDto(book);
    }

    @Override
    public List<BookDto> getBooks() {
        List<Book> bookList = bookRepository.findAll();

        return bookList.stream()
                .map(BookUtils::entityToDtoBookDto)
                .collect(Collectors.toList());
    }
}
