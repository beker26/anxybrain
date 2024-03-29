package br.com.anxybrain.post.service;

import br.com.anxybrain.comment.model.Comment;
import br.com.anxybrain.comment.repository.CommentRepository;
import br.com.anxybrain.exception.BusinessException;
import br.com.anxybrain.file.repository.FileRepository;
import br.com.anxybrain.post.model.Post;
import br.com.anxybrain.post.repository.PostRepository;
import br.com.anxybrain.post.request.PostRequest;
import br.com.anxybrain.post.response.PostResponse;
import br.com.anxybrain.user.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final FileRepository fileRepository;

    private final CommentRepository commentRepository;

    private final AuthService authService;

    @Transactional
    public PostResponse createPost(PostRequest postRequest, MultipartFile file) throws IOException {

        Post post = Post.toPostRequest(postRequest, authService.getCurrentUser());

        if (!file.isEmpty()) {
            post.setFile(fileRepository.save(createImagePost(file)));
        }

        Post savedPost = postRepository.save(post);

        return PostResponse.toPostResponse(savedPost);
    }

    public br.com.anxybrain.file.model.File createImagePost(MultipartFile file) throws IOException {

        String path = "src/main/resources/files/post"
                + "/" + authService.getCurrentUser().getUserName()
                + "/" + UUID.randomUUID()
                + "-" + file.getOriginalFilename();

        File convertFile = new File(path);

        if (!convertFile.getParentFile().exists()) {
            convertFile.getParentFile().mkdir();
        }

        convertFile.createNewFile();

        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
            return br.com.anxybrain.file.model.File.toFile(path);
        } catch (Exception exe) {
            exe.printStackTrace();
        }

        return null;
    }

    public void deletePost(String id) {

        Post postById = postRepository.findByIdAndUserUserName(id, authService.getCurrentUser().getUserName()).orElseThrow(() -> new BusinessException("Post not found"));

        br.com.anxybrain.file.model.File fileById = fileRepository.findById(postById.getFile().getId()).orElseThrow(() -> new BusinessException("File not found"));

        File convertFile = new File(postById.getFile().getPath());
        convertFile.delete();

        fileRepository.delete(fileById);

        postRepository.delete(postById);
    }

    public PostResponse findPost(String id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new BusinessException("Post not found"));

        return PostResponse.toPostResponse(post);
    }

    @Transactional
    public PostResponse editPost(String id, String text) {

        Post postById = postRepository.findByIdAndUserUserName(id, authService.getCurrentUser().getUserName()).orElseThrow(() -> new BusinessException("Post not found"));

        postById.setText(text);

        Post savedPost = postRepository.save(postById);

        return PostResponse.toPostResponse(savedPost);
    }

    public List<PostResponse> findAllPosts() {

        List<Post> findAll = postRepository.findAll();

        return findAll.stream().map(PostResponse::toPostResponse).collect(Collectors.toList());
    }

    public void deleteForComment(String idPost, String idComment) {

        Post postById = postRepository.findByIdAndUserUserName(idPost, authService.getCurrentUser().getUserName()).orElseThrow(() -> new BusinessException("Post not found")); // achou o post

        Comment comment = commentRepository.findByIdAndPostId(idComment, postById.getId()).orElseThrow(() -> new BusinessException("Comment not found"));

        commentRepository.delete(comment);

    }

    public List<PostResponse> postsForLogin() {

        List<Post> posts = postRepository.findByUserUserName(authService.getCurrentUser().getUserName());

        return posts.stream().map(PostResponse::toPostResponse).collect(Collectors.toList());
    }
}
