package br.com.anxybrain.comment.response;

import br.com.anxybrain.comment.model.Comment;
import br.com.anxybrain.post.model.Post;
import br.com.anxybrain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {

    private String id;

    private String text;

    private Post post;

    private Instant created;

    private User user;

    public static CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .post(comment.getPost())
                .created(comment.getCreated())
                .user(comment.getUser())
                .build();
    }
}
