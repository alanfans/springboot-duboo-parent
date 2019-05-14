package com.example.demo.provider.api.service.impl;

import com.example.demo.api.model.Bookimg;
import com.example.demo.api.service.BookImgService;
import com.example.demo.provider.dao.BookimgMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BookImgServiceImpl implements BookImgService {

    @Autowired
    BookimgMapper bookimgMapper;

    @Override
    public int addBookImg(Bookimg bookimg) {
        return bookimgMapper.insert(bookimg);
    }
}
