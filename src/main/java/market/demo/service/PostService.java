package market.demo.service;

import lombok.RequiredArgsConstructor;
import market.demo.Util.Utils;
import market.demo.dto.PostDTO;
import market.demo.entity.Post;
import market.demo.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<PostDTO> search(Map<String, Object> payload) {
        int page = (int) payload.getOrDefault("page", 0);
        int size = (int) payload.getOrDefault("size", 5);
        String search = (String) payload.getOrDefault("search", "");
        Integer status = (Integer) payload.getOrDefault("status", null);
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> data = postRepository.search(search, status, pageable);
        return data.map(Post::toDTO);
    }

    public List<PostDTO> getAllByUserId(Long userId) {
        List<Post> PostList = postRepository.findAllByUserId(userId);
        return PostList.stream().map(Post::toDTO).collect(Collectors.toList());
    }

    public List<PostDTO> getAll() {
        List<Post> postList = postRepository.findAllByStatus(Utils.ACTIVE);
        return postList.stream().map(Post::toDTO).collect(Collectors.toList());
    }

    public PostDTO create(PostDTO postDTO) {
        Optional<Post> maxIdSP = postRepository.findMaxId();
        Long maxId = maxIdSP.isPresent() ? maxIdSP.get().getId() + 1 : 1;
        Post post = Post.toEntity(postDTO);
        post.setPostCode("P" + maxId);
        post.setStatus(Utils.ACTIVE);
        return Post.toDTO(postRepository.save(post));
    }
}
