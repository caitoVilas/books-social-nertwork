package com.caito.booksnapi.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a book entity in the database.
 * This class is used to map the 'books' table in the database.
 *
 *
 * @author caito
 *
 */
@Entity
@Table(name = "books")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
@EntityListeners({AuditingEntityListener.class})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    @Column(length = 1500)
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserApp owner;
    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;
    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;
    @Transient
    public Double getRate(){
        if (feedbacks == null || feedbacks.isEmpty())
            return 0.0;
        var rate = feedbacks.stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
        Double rateRounded = Math.round(rate * 10.0) / 10.0; // Round to one decimal place
        return  rateRounded;
    }
}
