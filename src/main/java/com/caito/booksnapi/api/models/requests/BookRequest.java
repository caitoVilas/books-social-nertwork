package com.caito.booksnapi.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a request to create or update a book.
 * This class is used in the BookController to handle book-related requests.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class BookRequest implements Serializable {
    private String title;
    private String author;
    private String isbn;
    private String synopsis;
    private boolean shareable = true;
}
