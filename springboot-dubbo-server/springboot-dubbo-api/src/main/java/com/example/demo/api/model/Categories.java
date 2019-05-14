package com.example.demo.api.model;

import io.swagger.annotations.ApiModel;

@ApiModel("Categories")
public class Categories extends BaseDO {

    private String name;

    private String display;

    private Integer type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display == null ? null : display.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}