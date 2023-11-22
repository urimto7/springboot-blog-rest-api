package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    //create blog post rest api
    @PostMapping

    public ResponseEntity<PostDto> createPost(@RequestBody
                                              PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts rest api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir

    ){


        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);

    }
    //get post by id rest api
    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> getPostById(@PathVariable(name = "id")Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    // update post rest api by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable(name="id") long id){
        PostDto postReesponse = postService.updatePost(postDto,id);
        return new ResponseEntity<>(postReesponse, HttpStatus.OK);

    }
    // delete post rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id")long id){
        postService.deletePostByID(id);
        return new ResponseEntity<>("Post deletet Succesfullyu", HttpStatus.OK);


    }
}