package br.com.anxybrain.like.controller;

import br.com.anxybrain.comment.response.CommentResponse;
import br.com.anxybrain.like.response.LikeResponse;
import br.com.anxybrain.like.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/like")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public ResponseEntity<LikeResponse> likeToPost(@PathVariable String postId) {
        return new ResponseEntity<>(likeService.likeToPost(postId), HttpStatus.CREATED);
    }

    @DeleteMapping("/unlike/{id}")
    public ResponseEntity<Void> unlike(@PathVariable String id) {
        likeService.unlike(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("likes/{id}")
    public ResponseEntity<List<LikeResponse>> findByLikesForPost(@PathVariable String id) {
        return new ResponseEntity<>(likeService.findByLikesForPost(id), HttpStatus.OK);
    }
}
