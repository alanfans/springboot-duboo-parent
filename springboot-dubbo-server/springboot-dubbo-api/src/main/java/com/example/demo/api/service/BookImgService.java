package com.example.demo.api.service;

import com.example.demo.api.model.Bookimg;

import javax.ws.rs.Path;

//@Path("bookImgService") // #1
public interface BookImgService {

    int addBookImg(Bookimg bookimg);
}
