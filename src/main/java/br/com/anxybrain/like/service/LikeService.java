package br.com.anxybrain.like.service;

import br.com.anxybrain.exception.BusinessException;
import br.com.anxybrain.like.model.Like;
import br.com.anxybrain.like.repository.LikeRepository;
import br.com.anxybrain.like.response.LikeResponse;
import br.com.anxybrain.post.model.Post;
import br.com.anxybrain.post.repository.PostRepository;
import br.com.anxybrain.user.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService {


    private final LikeRepository likeRepository;

    private final PostRepository postRepository;

    private final AuthService authService;

    public LikeResponse likeToPost(String postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new BusinessException("Post not found"));

        Like toLike = Like.toLike(true, authService.getCurrentUser(), post);

        Like savedLike = likeRepository.save(toLike);

        return LikeResponse.toLikeResponse(savedLike);
    }

    public void unlike(String id) {

        Like like = likeRepository.findByIdAndUserUserName(id, authService.getCurrentUser().getUserName()).orElseThrow(() -> new BusinessException("Like not found"));

        likeRepository.delete(like);
    }

    public List<LikeResponse> findByLikesForPost(String id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new BusinessException("Post not found"));

        return likeRepository.findByPostOrderByCreatedDesc(post)
                .stream()
                .map(LikeResponse::toLikeResponse)
                .toList();
    }
}
