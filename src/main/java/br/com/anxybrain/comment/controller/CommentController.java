package br.com.anxybrain.comment.controller;


import br.com.anxybrain.comment.request.CommentRequest;
import br.com.anxybrain.comment.response.CommentResponse;
import br.com.anxybrain.comment.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping(value = "/{idPost}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentResponse> createCommentToPost(@RequestBody CommentRequest commentRequest, @PathVariable String idPost) {
        return new ResponseEntity<>(commentService.createCommentToPost(commentRequest, idPost), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponse> editComment(@PathVariable String id, @PathVariable String text) {
        return new ResponseEntity<>(commentService.editComment(id, text), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable String id) {

        commentService.deleteComment(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("comments/{id}")
    public ResponseEntity<List<CommentResponse>> findByCommentsForPost(@PathVariable String id) {
        return new ResponseEntity<>(commentService.findByCommentsForPost(id), HttpStatus.OK);
    }
}
