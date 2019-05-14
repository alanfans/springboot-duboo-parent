package com.example.demo.provider.api.service.impl;

import com.example.demo.api.model.Book;
import com.example.demo.api.service.BookService;
import com.example.demo.provider.dao.BookMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public int addBook(Book book) {
        bookMapper.insert(book);
        return book.getId();
    }

    @Override
    public Book selectbyName(String name) {
        return bookMapper.selectbyName(name);
    }
}
