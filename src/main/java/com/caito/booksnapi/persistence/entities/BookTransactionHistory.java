package com.caito.booksnapi.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Represents a book transaction history entity in the database.
 * This class is used to map the 'book_transaction_history' table in the database.
 *
 * @author caito
 */
@Entity
@Table(name = "book_transaction_history")
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@Builder
public class BookTransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserApp user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private boolean returned;
    private boolean returnApproved;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
