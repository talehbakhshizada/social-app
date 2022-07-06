package com.company.socialapp.service;

import com.company.socialapp.dto.CommentCreateDTO;
import com.company.socialapp.dto.CommentUpdateDTO;
import com.company.socialapp.entity.Comment;
import com.company.socialapp.entity.Post;
import com.company.socialapp.entity.User;
import com.company.socialapp.exception.NotFoundException;
import com.company.socialapp.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> comments;
        if (userId.isPresent() && postId.isPresent()) {
            return commentRepository.findByUserIdAndPostId(userId, postId);
        } else if (userId.isPresent()) {
            comments = commentRepository.findByUserId(userId);
        } else if (postId.isPresent()) {
            comments = commentRepository.findByPostId(postId);
        } else {
            return comments = commentRepository.findAll();
        }
        return comments;
    }


    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("COMMENT NOT FOUND"));
    }

    public Comment createComment(CommentCreateDTO commentCreateDTO) {
        User user = userService.getUserById(commentCreateDTO.getUserId());
        Post post = postService.getPostById(commentCreateDTO.getPostId());
        if (user != null && post != null) {
            Comment toSave = new Comment();
            toSave.setId(commentCreateDTO.getId());
            toSave.setPost(post);
            toSave.setUser(user);
            toSave.setText(commentCreateDTO.getText());
            return commentRepository.save(toSave);
        } else {
            throw new NotFoundException("USER or POST NOT FOUND");
        }
    }

    public Comment updateComment(Long commentId, CommentUpdateDTO updateComment) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment toUpdate = comment.get();
            toUpdate.setText(updateComment.getText());
            return commentRepository.save(toUpdate);
        }
        throw new NotFoundException("COMMENT NOT FOUND");
    }

    public void deleteComment(Long commentId) {
            Optional<Comment> comment = commentRepository.findById(commentId);
        if ((comment.isPresent())) {
            commentRepository.delete(comment.get());
        } else {
            throw new NotFoundException("COMMENT NOT FOUND");
        }
    }
}
