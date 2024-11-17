package com.example.booksService.controller;

import com.example.booksService.entity.Author;
import com.example.booksService.entity.Book;
import com.example.booksService.service.AuthorService;
import com.example.booksService.service.BookService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;


    @PostMapping("/author/{authorName}")
    public ResponseEntity<Book> createBook(@RequestBody Book book, @PathVariable String authorName) {
        System.out.println("Received request to create book for author: " + authorName);
        try {
            Book savedBook = bookService.saveBook(book, authorName);
            System.out.println("Book saved successfully: " + savedBook);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            System.out.println("Author not found: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        try {

            Author savedAuthor = authorService.saveAuthor(author);
            return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


  /*  @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
*/
    @GetMapping("/author/{authorName}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String authorName) {
        List<Book> books = bookService.getBooksByAuthor(authorName);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // GET: Get book by code
    @GetMapping("/code/{code}")
    public ResponseEntity<Book> getBookByCode(@PathVariable String code) {
        Book book = bookService.getBookByCode(code);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // GET: Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{authorId}/books")
    public ResponseEntity<List<Book>> getBooksByAuthorId(@PathVariable Long authorId) {
        try {
            List<Book> books = authorService.getBooksByAuthorId(authorId);
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT: Update book details
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

}
