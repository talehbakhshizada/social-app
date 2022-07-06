package com.company.socialapp.controller;

import com.company.socialapp.dto.CommentCreateDTO;
import com.company.socialapp.dto.CommentUpdateDTO;
import com.company.socialapp.entity.Comment;
import com.company.socialapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(@RequestParam Optional<Long> userId,
                                                        @RequestParam Optional<Long> postId) {
        return new ResponseEntity<>(commentService.getAllCommentsWithParam(userId, postId), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(commentId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentCreateDTO commentCreateDTO) {
        return new ResponseEntity<>(commentService.createComment(commentCreateDTO),HttpStatus.CREATED);
    }


    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateDTO updateComment) {
        return new ResponseEntity<>(commentService.updateComment(commentId, updateComment),HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteOneComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
