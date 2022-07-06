package com.company.socialapp.service;

import com.company.socialapp.dto.PostCreateDTO;
import com.company.socialapp.dto.PostUpdateDTO;
import com.company.socialapp.entity.Post;
import com.company.socialapp.entity.User;
import com.company.socialapp.exception.NotFoundException;
import com.company.socialapp.repository.PostRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if (userId.isPresent()) {
            List<Post> posts = postRepository.findByUserId(userId);
            if (posts == null) {
                return postRepository.findAll();
            }
        }
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("POST NOT FOUND"));
    }

    public Post createPost(PostCreateDTO newPostCreateDTO) {
        User user = userService.getUserById(newPostCreateDTO.getUserId());
        if (user == null) {
            throw new NotFoundException("USER NOT FOUND");
        } else {
            Post toSave = new Post();
            toSave.setId(newPostCreateDTO.getId());
            toSave.setText(newPostCreateDTO.getText());
            toSave.setTitle(newPostCreateDTO.getTitle());
            toSave.setUser(user);
            return postRepository.save(toSave);
        }
    }

    public Post updatePost(Long postId, PostUpdateDTO updatePost) {
        Optional<Post> oldPost = postRepository.findById(postId);
        if (oldPost.isPresent()) {
            Post toUpdate = oldPost.get();
            toUpdate.setTitle(updatePost.getTitle());
            toUpdate.setText(updatePost.getText());
            return postRepository.save(toUpdate);
        }
        throw new NotFoundException("POST NOT FOUND");
    }

    public void deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if ((post.isPresent())) {
            postRepository.delete(post.get());
        } else {
            throw new NotFoundException("POST NOT FOUND");
        }
    }
}
