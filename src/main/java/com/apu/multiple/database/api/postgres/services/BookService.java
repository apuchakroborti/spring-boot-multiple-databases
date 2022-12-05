package com.apu.multiple.database.api.postgres.services;

import com.apu.multiple.database.api.postgres.dto.BookDto;
import java.util.List;

public interface BookService {
    BookDto addBook(BookDto bookDto);
    List<BookDto> getBooks();
}
