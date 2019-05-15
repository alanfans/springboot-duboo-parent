package com.example.demo.api.service;

import com.example.demo.api.model.Book;

import javax.ws.rs.Path;

//@Path("bookService")
public interface BookService {
    int addBook(Book book);

    Book selectbyName(String name);

}
