package com.example.booksService.service;

import com.example.booksService.entity.Author;
import com.example.booksService.entity.Book;
import com.example.booksService.repository.AuthorRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    // Save an author
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Book> getBooksByAuthorId(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with ID: " + authorId));
        return author.getBooks();
    }

    // Retrieve all authors
   /* public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }*/
}
