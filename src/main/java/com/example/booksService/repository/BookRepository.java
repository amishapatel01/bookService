package com.example.booksService.repository;

import com.example.booksService.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorName(String authorName);
    Book findByCode(String code);
}
