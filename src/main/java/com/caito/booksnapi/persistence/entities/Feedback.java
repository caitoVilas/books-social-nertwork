package com.caito.booksnapi.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
@EntityListeners({AuditingEntityListener.class})
public class Feedback {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Double note; // 1 to 5 stars
    private String comment;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

}
