package com.caito.booksnapi.api.controllers.impl;

import com.caito.booksnapi.api.controllers.contracts.BookController;
import com.caito.booksnapi.api.models.requests.BookRequest;
import com.caito.booksnapi.api.models.responses.BookResponse;
import com.caito.booksnapi.services.contracts.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of the BookController interface.
 * This class handles book-related operations such as creating a book.
 *
 * @author caito
 *
 */
@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Books API")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class BookControllerImpl implements BookController {
    private final BookService bookService;

    @Override
    public ResponseEntity<?> createBook(BookRequest request, Authentication conectedUser) {
        bookService.createBook(request, conectedUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<BookResponse> getBookById(Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @Override
    public ResponseEntity<Page<BookResponse>> getAll(int page, int size) {
    Page<BookResponse> books = bookService.getAll(page, size);
    if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(books);
    }
}
