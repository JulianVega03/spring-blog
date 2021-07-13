package com.juliandev.techie.springblog.service;

import com.juliandev.techie.springblog.dto.PostDto;
import com.juliandev.techie.springblog.exception.PostNotFoundException;
import com.juliandev.techie.springblog.model.Post;
import com.juliandev.techie.springblog.repository.PostRepository;
import com.juliandev.techie.springblog.service.AuthService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    public void createPost(PostDto postDto){
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList());
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User user = authService.getCurrentUser().orElseThrow(()-> new IllegalArgumentException("Usuario No Logeado"));
        post.setUsername(user.getUsername());
        post.setCreatedOn(Instant.now());
        return post;
    }

    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException("No encontrado "+ id));
        return mapFromPostToDto(post);
    }
}
