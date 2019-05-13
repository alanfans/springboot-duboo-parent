package com.example.demo.api.model;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by hylc on 2018/8/19.
 */
@Data
public class User implements Serializable {

    private Integer id;
    private String user_name;

    public User(Integer id, String user_name) {
        this.id = id;
        this.user_name = user_name;
    }

    public User() {

    }
}
