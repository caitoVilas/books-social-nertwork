package com.caito.booksnapi.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Represents a response model for book details.
 * This class is used to encapsulate the data returned when fetching book information.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class BookResponse implements Serializable {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private String coverImageUrl;
    private String owner;
    private Double rate;
    private boolean isPublic;
    private boolean isAvailable;
}
