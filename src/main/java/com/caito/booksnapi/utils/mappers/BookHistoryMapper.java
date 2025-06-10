package com.caito.booksnapi.utils.mappers;

import com.caito.booksnapi.api.models.responses.BorrowedResponse;
import com.caito.booksnapi.persistence.entities.BookTransactionHistory;

/**
 * Mapper class for converting BookTransactionHistory entities to BorrowedResponse DTOs.
 * This class provides methods to map the properties of BookTransactionHistory to BorrowedResponse.
 *
 * @author caito
 *
 */
public class BookHistoryMapper {

    /**
     * Maps a BookTransactionHistory entity to a BorrowedResponse DTO.
     *
     * @param bth the BookTransactionHistory entity to be mapped
     * @return a BorrowedResponse DTO containing the mapped properties
     */
    public static BorrowedResponse mapToDto(BookTransactionHistory bth){
        return BorrowedResponse.builder()
                .id(bth.getId())
                .title(bth.getBook().getTitle())
                .author(bth.getBook().getAuthor())
                .isbn(bth.getBook().getIsbn())
                .rate(bth.getBook().getRate())
                .returned(bth.isReturned())
                .returnApproved(bth.isReturnApproved())
                .build();
    }
}
