package com.caito.booksnapi.persistence.repositories;

import com.caito.booksnapi.persistence.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
}
