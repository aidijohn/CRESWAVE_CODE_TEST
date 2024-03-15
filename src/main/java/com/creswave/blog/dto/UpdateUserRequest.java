package com.creswave.blog.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstname;
    private String secondname;
    private String email;
    private String password;
}

