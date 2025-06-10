package com.caito.booksnapi.utils.mappers;

import com.caito.booksnapi.api.models.requests.BookRequest;
import com.caito.booksnapi.api.models.responses.BookResponse;
import com.caito.booksnapi.persistence.entities.Book;

/**
 * Mapper class to convert BookRequest to Book entity.
 * This class is used to map the data from the request to the entity.
 *
 * @author caito
 *
 *
 */
public class BookMapper {

    /**
     * Maps a BookRequest to a Book entity.
     * @param request BookRequest object containing book details
     * @return Book entity
     */
    public static Book mapToEntity(BookRequest request){
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .synopsis(request.getSynopsis())
                .archived(false) // Default value
                .shareable(request.isShareable()) // Default value
                .build();
    }

    /**
     * Maps a Book entity to a BookResponse DTO.
     * @param book Book entity to be converted
     * @return BookResponse DTO containing book details
     */
    public static BookResponse mapToDto(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .description(book.getSynopsis())
                .owner(book.getOwner().fullname())
                .rate(book.getRate())
                .isPublic(book.isShareable())
                .isAvailable(book.isArchived()) // Assuming archived means not available
                .build();
    }
}
