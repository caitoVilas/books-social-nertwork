package com.caito.booksnapi.services.contracts;

import com.caito.booksnapi.api.models.requests.BookRequest;
import com.caito.booksnapi.api.models.responses.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

/**
 * Service interface for managing books.
 * This interface defines the methods for book-related operations.
 *
 * @author caito
 *
 */
public interface BookService {
    void  createBook(BookRequest request, Authentication conectedUser);
    BookResponse getById(Long id);
    Page<BookResponse> getAll(int page, int size);
}
