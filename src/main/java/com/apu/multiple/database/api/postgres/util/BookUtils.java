package com.apu.multiple.database.api.postgres.util;

import com.apu.multiple.database.api.postgres.dto.BookDto;
import com.apu.multiple.database.api.postgres.entity.Book;

public class BookUtils {
    public static Book dtoToEntityBook(BookDto bookDto){
        return new Book(bookDto.getId(), bookDto.getName());
    }
    public static BookDto entityToDtoBookDto(Book book){
        return new BookDto(book.getId(), book.getName());
    }

}
