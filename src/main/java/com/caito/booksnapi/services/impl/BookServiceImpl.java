package com.caito.booksnapi.services.impl;

import com.caito.booksnapi.api.exceptions.customs.BadRequestException;
import com.caito.booksnapi.api.exceptions.customs.NotFoundException;
import com.caito.booksnapi.api.models.requests.BookRequest;
import com.caito.booksnapi.api.models.responses.BookResponse;
import com.caito.booksnapi.persistence.entities.UserApp;
import com.caito.booksnapi.persistence.repositories.BookRepository;
import com.caito.booksnapi.services.contracts.BookService;
import com.caito.booksnapi.utils.logs.WriteLog;
import com.caito.booksnapi.utils.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the BookService interface.
 * This class provides methods for managing books, including creating, retrieving, and validating books.
 *
 * @author caito
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    /**
     * Creates a new book.
     *
     * @param request the book request containing book details
     * @param conectedUser the authenticated user creating the book
     * @throws BadRequestException if the book request is invalid
     */
    @Override
    @Transactional
    public void createBook(BookRequest request, Authentication conectedUser) {
        log.info(WriteLog.logInfo("Creating book service"));
        UserApp user = (UserApp) conectedUser.getPrincipal();
        validateBok(request);
        var book = BookMapper.mapToEntity(request);
        book.setOwner(user);
        bookRepository.save(book);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return a BookResponse object containing the book details
     * @throws NotFoundException if the book is not found
     */
    @Override
    @Transactional(readOnly = true)
    public BookResponse getById(Long id) {
        log.info(WriteLog.logInfo("Fetching book by ID: " + id));
        return BookMapper.mapToDto( bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with ID: " + id)));

    }

    /**
     * Retrieves all books with pagination.
     *
     * @param page the page number to retrieve
     * @param size the number of items per page
     * @return a paginated list of BookResponse objects
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> getAll(int page, int size) {
        log.info(WriteLog.logInfo("Fetching all books with pagination"));
        PageRequest pr = PageRequest.of(page, size);
        return bookRepository.findAll(pr)
                .map(BookMapper::mapToDto);
    }

    /**
     * Validates the book request.
     *
     * @param request the book request to validate
     * @throws BadRequestException if validation fails
     */
    private void validateBok(BookRequest request) {
        List<String> errors = new ArrayList<>();

        // Validate title
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            errors.add("Title is required.");
        };
        // Validate author
        if (request.getAuthor() == null || request.getAuthor().isEmpty()) {
            errors.add("Author is required.");
        }
        // Validate ISBN
        if (request.getIsbn() == null || request.getIsbn().isEmpty()) {
            errors.add("ISBN is required.");
        }

        if (!errors.isEmpty()){
            log.error(WriteLog.logError("Book validation failed: " + String.join(", ", errors)));
            throw new BadRequestException(errors);
        }

    }
}
