package com.bjpowernode.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;
    private String username;

    public User() {
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}