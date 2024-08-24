package market.demo.service;

import lombok.RequiredArgsConstructor;
import market.demo.Util.MapperUtils;
import market.demo.Util.Utils;
import market.demo.dto.PostDTO;
import market.demo.dto.response.PostResponse;
import market.demo.entity.Image;
import market.demo.entity.Post;
import market.demo.repository.ImageRepository;
import market.demo.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public final ImageRepository imageRepository;

    public Page<PostDTO> search(Map<String, Object> payload,Long uid) {
        int page = (int) payload.getOrDefault("page", 0);
        int size = (int) payload.getOrDefault("size", 5);
        String search = (String) payload.getOrDefault("search", "");
        Integer status = (Integer) payload.getOrDefault("status", null);
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> data = postRepository.search(search, status, pageable,uid);
        return data.map(Post::toDTO);
    }

    public List<PostDTO> getAllByUserId(Long userId) {
        List<Post> PostList = postRepository.findAllByUserId(userId);
        return PostList.stream().map(Post::toDTO).collect(Collectors.toList());
    }

    public List<?> getAllByStatusActive() {
        List<Post> posts = postRepository.findTop20ByStatusOrderByCreatedAtDesc(Utils.Status.ACTIVE);
        List<Image> images = imageRepository.findAllByStatus(Utils.Status.ACTIVE);
        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : posts) {
            PostResponse postResponse = MapperUtils.mapCommonFields(post, PostResponse.class);

            for (Image image : images) {
                if (post.getId().equals(image.getPostId())&&image.getState().equals(Utils.StateImage.MAIN)) {
                    postResponse.setImageUrl(image.getUrl());
                    break;
                }
            }
            postResponses.add(postResponse);
        }
        return postResponses;
    }

        public PostDTO create(PostDTO postDTO) {
        Optional<Post> maxIdSP = postRepository.findMaxId();
        Long maxId = maxIdSP.isPresent() ? maxIdSP.get().getId() + 1 : 1;
        Post post = Post.toEntity(postDTO);
        post.setPostCode("P" + maxId);
        post.setUserId(1l);
        post.setCreateBy(1l);
        post.setCreatedAt(LocalDateTime.now());
        post.setStatusPost(Utils.Status.ACTIVE);
        post.setStatus(Utils.Status.ACTIVE);
        return Post.toDTO(postRepository.save(post));
    }
}
