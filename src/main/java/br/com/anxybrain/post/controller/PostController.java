package br.com.anxybrain.post.controller;

import br.com.anxybrain.post.request.PostRequest;
import br.com.anxybrain.post.response.PostResponse;
import br.com.anxybrain.post.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/createPost")
    public ResponseEntity<PostResponse> createPost(@RequestPart PostRequest postRequest, @RequestPart MultipartFile file) throws IOException {
        return new ResponseEntity<>(postService.createPost(postRequest, file), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/{text}")
    public ResponseEntity<PostResponse> editPost(@PathVariable String id, @PathVariable String text) {
        return new ResponseEntity<>(postService.editPost(id, text), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable String id) {
        return new ResponseEntity<>(postService.findPost(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAllPosts() {
        return new ResponseEntity<>(postService.findAllPosts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }


}
