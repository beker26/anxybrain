package br.com.anxybrain.comment.service;

import br.com.anxybrain.comment.model.Comment;
import br.com.anxybrain.comment.repository.CommentRepository;
import br.com.anxybrain.comment.request.CommentRequest;
import br.com.anxybrain.comment.response.CommentResponse;
import br.com.anxybrain.exception.BusinessException;
import br.com.anxybrain.post.model.Post;
import br.com.anxybrain.post.repository.PostRepository;
import br.com.anxybrain.user.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private final AuthService authService;

    @Transactional
    public CommentResponse createCommentToPost(CommentRequest commentRequest, String idPost) {

        Post post = postRepository.findById(idPost).orElseThrow(() -> new BusinessException("Post not Found"));

        Comment comment = Comment.toComment(authService.getCurrentUser(),post, commentRequest);

        commentRepository.save(comment);

        return CommentResponse.toCommentResponse(comment);
    }

    @Transactional
    public CommentResponse editComment(String id, String text) {

        Comment comment = commentRepository.findByIdAndUserUserName(id,authService.getCurrentUser().getUserName()).orElseThrow(() -> new BusinessException("Comment not found"));

        comment.setText(text);

        commentRepository.save(comment);
        
        return CommentResponse.toCommentResponse(comment);
    }

    public void deleteComment(String id) {

        Comment comment = commentRepository.findByIdAndUserUserName(id,authService.getCurrentUser().getUserName()).orElseThrow(() -> new BusinessException("Comment not found"));

        commentRepository.delete(comment);
    }

    public List<CommentResponse> findByCommentsForPost(String id) {

        Post findPost = postRepository.findById(id).orElseThrow(() -> new BusinessException("Post not found"));

        return commentRepository.findByPostOrderByCreatedDesc(findPost)
                .stream()
                .map(CommentResponse::toCommentResponse)
                .toList();
    }
}
