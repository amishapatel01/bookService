package com.example.booksService.service;

import com.example.booksService.entity.Author;
import com.example.booksService.entity.Book;
import com.example.booksService.repository.AuthorRepository;
import com.example.booksService.repository.BookRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public Book saveBook(Book book, String authorName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    public Book getBookByCode(String code) {
        return bookRepository.findByCode(code);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setName(updatedBook.getName());
        existingBook.setCode(updatedBook.getCode());
        return bookRepository.save(existingBook);
    }
}

