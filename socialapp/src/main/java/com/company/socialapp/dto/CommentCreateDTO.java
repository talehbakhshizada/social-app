package com.company.socialapp.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private String text;
}
