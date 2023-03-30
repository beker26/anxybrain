package br.com.anxybrain.comment.model;

import br.com.anxybrain.comment.request.CommentRequest;
import br.com.anxybrain.post.model.Post;
import br.com.anxybrain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comment")
public class Comment {

    @Id
    private String id;

    private String text;

    private Post post;

    private Instant created;

    private User user;

    public static Comment toComment(User currentUser, Post post, CommentRequest commentRequest) {
        return Comment.builder()
                .text(commentRequest.getText())
                .post(post)
                .user(currentUser)
                .created(Instant.now())
                .build();
    }
}
