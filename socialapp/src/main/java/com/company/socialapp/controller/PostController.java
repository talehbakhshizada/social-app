package com.company.socialapp.controller;

import com.company.socialapp.dto.PostCreateDTO;
import com.company.socialapp.dto.PostUpdateDTO;
import com.company.socialapp.entity.Post;
import com.company.socialapp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    // localhost:8080/posts?userId=1  or   localhost:8080/posts
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam Optional<Long> userId) {
        return new ResponseEntity<>(postService.getAllPosts(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable(value = "id") Long postId) {    //fix
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostCreateDTO newPostCreateDTO) {
        return new ResponseEntity<>(postService.createPost(newPostCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable(value = "id") Long postId, @RequestBody PostUpdateDTO updatePost) {
        return new ResponseEntity<>(postService.updatePost(postId,updatePost), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "id") Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
