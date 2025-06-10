package com.caito.booksnapi.services;

import com.caito.booksnapi.api.exceptions.customs.BadRequestException;
import com.caito.booksnapi.api.exceptions.customs.NotFoundException;
import com.caito.booksnapi.api.models.requests.BookRequest;
import com.caito.booksnapi.persistence.entities.Book;
import com.caito.booksnapi.persistence.entities.UserApp;
import com.caito.booksnapi.persistence.repositories.BookRepository;
import com.caito.booksnapi.services.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * * Unit tests for the BookServiceImpl class.
 * This class tests the methods for creating and fetching books.
 *
 * @author caito
 *
 */
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Creating book with valid request")
    @Test
    void creatingBookWithValidRequest() {
        BookRequest request = BookRequest.builder()
                .title("Title")
                .author("Author")
                .isbn("ISBN")
                .build();
        UserApp user = new UserApp();
        when(authentication.getPrincipal()).thenReturn(user);
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        assertDoesNotThrow(() -> bookService.createBook(request, authentication));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("Creating book with invalid request throws BadRequestException")
    @Test
    void creatingBookWithInvalidRequestThrowsBadRequestException() {
        BookRequest request = new BookRequest();
        UserApp user = new UserApp();
        when(authentication.getPrincipal()).thenReturn(user);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> bookService.createBook(request, authentication));
        assertTrue(exception.getErrors().contains("Title is required."));
        assertTrue(exception.getErrors().contains("Author is required."));
        assertTrue(exception.getErrors().contains("ISBN is required."));
    }


    @DisplayName("Fetching book by invalid ID throws NotFoundException")
    @Test
    void fetchingBookByInvalidIdThrowsNotFoundException() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookService.getById(id));
        assertEquals("Book not found with ID: " + id, exception.getMessage());
    }


}
