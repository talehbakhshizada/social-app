package com.company.socialapp.dto;

import lombok.Data;

@Data
public class PostCreateDTO {
    private Long id;
    private String text;
    private String title;
    private Long userId;
}
