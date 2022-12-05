package com.apu.multiple.database.api.postgres.controllers;

import com.apu.multiple.database.api.postgres.dto.BookDto;
import com.apu.multiple.database.api.postgres.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public BookDto addBook(@RequestBody BookDto bookDto){
        return bookService.addBook(bookDto);
    }

    @GetMapping("/findBooks")
    public List<BookDto> findBooks(){
        return bookService.getBooks();
    }
}
