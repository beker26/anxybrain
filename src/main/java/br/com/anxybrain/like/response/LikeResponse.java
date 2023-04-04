package br.com.anxybrain.like.response;

import br.com.anxybrain.like.model.Like;
import br.com.anxybrain.post.model.Post;
import br.com.anxybrain.post.response.PostResponse;
import br.com.anxybrain.user.domain.User;
import br.com.anxybrain.user.repository.UserRepository;
import br.com.anxybrain.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeResponse {

    private String id;

    private Boolean like;

    private Instant created;

    private User user;

    private PostResponse post;

    public static LikeResponse toLikeResponse(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .like(like.getLike())
                .created(like.getCreated())
                .user(like.getUser())
                .post(PostResponse.toPostResponse(like.getPost()))
                .build();
    }
}
