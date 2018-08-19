package com.example.demo.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by hylc on 2018/8/19.
 */
@Data
public class User implements Serializable {

    private Integer id;
    private String user_name;
}
