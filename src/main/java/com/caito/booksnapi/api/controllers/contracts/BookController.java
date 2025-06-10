package com.caito.booksnapi.api.controllers.contracts;

import com.caito.booksnapi.api.models.requests.BookRequest;
import com.caito.booksnapi.api.models.responses.BookResponse;
import com.caito.booksnapi.api.models.responses.BorrowedResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Interface for the BookController.
 * This interface defines the contract for book-related operations.
 *
 * @author caito
 *
 */
public interface BookController {

    @PostMapping("/create")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<?> createBook(@RequestBody BookRequest request, Authentication conectedUser);

    @GetMapping("/id/{id}")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id);

    @GetMapping("/all")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<Page<BookResponse>> getAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size);

    @GetMapping("/owner/{ownerId}")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<Page<BookResponse>> getBooksByOwner(@PathVariable Long ownerId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size);

    @GetMapping("/borrowed")
    @SecurityRequirement(name = "security token")
    public ResponseEntity<Page<BorrowedResponse>> getBorrowedBooks(Authentication conectedUser,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size);
}
