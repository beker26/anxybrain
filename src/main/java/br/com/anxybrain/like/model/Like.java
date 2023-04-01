package br.com.anxybrain.like.model;

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
@Document(collection = "like")
public class Like {
    @Id
    private String id;

    private Boolean like;

    private Instant created;

    private User user;

    private Post post;

    public static Like toLike(boolean like, User user, Post post) {
        return Like.builder()
                .like(like)
                .user(user)
                .post(post)
                .created(Instant.now())
                .build();
    }
}
