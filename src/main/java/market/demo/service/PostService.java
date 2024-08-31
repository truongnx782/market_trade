package market.demo.service;

import lombok.RequiredArgsConstructor;
import market.demo.Util.MapperUtils;
import market.demo.Util.Utils;
import market.demo.dto.PostDTO;
import market.demo.dto.UserDTO;
import market.demo.dto.response.PostResponse;
import market.demo.entity.Image;
import market.demo.entity.Post;
import market.demo.repository.ImageRepository;
import market.demo.repository.PostRepository;
import market.demo.repository.httpClient.Market_authClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final Market_authClient market_authClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Page<PostDTO> searchPostByAdmin(Map<String, Object> payload) {
        int page = (int) payload.getOrDefault("page", 0);
        int size = (int) payload.getOrDefault("size", 5);
        String search = (String) payload.getOrDefault("search", "");
        Integer statusPost = payload.get("categoryId") != null ? Integer.valueOf(payload.get("statusPoststatusPost").toString()) : null;
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> data = postRepository.searchPostByAdmin(search, statusPost, pageable);
        return data.map(Post::toDTO);
    }

    public List<?> getAllByStatusActive() {
        List<Post> posts = postRepository.findTop20ByStatusAndStatusPostOrderByCreatedAtDesc(Utils.Status.ACTIVE,Utils.StatusPost.ACTIVE);
        List<Image> images = imageRepository.findAllByStatus(Utils.Status.ACTIVE);
        List<Long>userIds = posts.stream().map(x->x.getUserId()).collect(Collectors.toList());
        List<UserDTO> userDTOS =market_authClient.getAllUserById(userIds);
        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : posts) {
            PostResponse postResponse = MapperUtils.mapCommonFields(post, PostResponse.class);

            for (UserDTO userDTO: userDTOS){
                if(userDTO.getId().equals(post.getUserId())){
                    postResponse.setPhoneNumber(userDTO.getPhoneNumber());
                    break;
                }
            }

            for (Image image : images) {
                if (post.getId().equals(image.getPostId()) && image.getState().equals(Utils.StateImage.MAIN)) {
                    postResponse.setImageUrl(image.getUrl());
                    break;
                }
            }
            postResponses.add(postResponse);
        }
        return postResponses;
    }

    public Page<PostDTO> searchPostByUid(Map<String,Object> payload,Long userId) {
        int page = (int) payload.getOrDefault("page", 0);
        int size = (int) payload.getOrDefault("size", 5);
        String search = (String) payload.getOrDefault("search", "");
        Integer status = (Integer) payload.getOrDefault("status", null);
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> data = postRepository.searchPostByUid(search,status,pageable,userId);
        return data.map(Post::toDTO);
    }

    public Page<PostResponse> searchPost(Map<String, Object> payload) {
        int page = (int) payload.getOrDefault("page", 0);
        int size = (int) payload.getOrDefault("size", 5);
        String search = (String) payload.getOrDefault("search", "");
        Integer statusPost = (Integer) payload.getOrDefault("statusPost", Utils.StatusPost.ACTIVE);
        Long categoryId = payload.get("categoryId") != null ? Long.valueOf(payload.get("categoryId").toString()) : null;
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> data = postRepository.searchPost(search, statusPost, categoryId,pageable);

        List<Image> images = imageRepository.findAllByStatus(Utils.Status.ACTIVE);
        List<Long>userIds = data.stream().map(x->x.getUserId()).collect(Collectors.toList());
        List<UserDTO> userDTOS =market_authClient.getAllUserById(userIds);
        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : data) {
            PostResponse postResponse = MapperUtils.mapCommonFields(post, PostResponse.class);

            for (UserDTO userDTO: userDTOS){
                if(userDTO.getId().equals(post.getUserId())){
                    postResponse.setPhoneNumber(userDTO.getPhoneNumber());
                    break;
                }
            }

            for (Image image : images) {
                if (post.getId().equals(image.getPostId()) && image.getState().equals(Utils.StateImage.MAIN)) {
                    postResponse.setImageUrl(image.getUrl());
                    break;
                }
            }
            postResponses.add(postResponse);
        }
        return new PageImpl<>(postResponses, pageable, data.getTotalElements());

    }


    public PostDTO create(PostDTO postDTO, Long uid) {
        PostDTO.validate(postDTO);
        Optional<Post> maxIdSP = postRepository.findMaxId();
        Long maxId = maxIdSP.isPresent() ? maxIdSP.get().getId() + 1 : 1;
        Post post = Post.toEntity(postDTO);
        post.setPostCode("P" + maxId);
        post.setUserId(uid);
        post.setCreateBy(uid);
        post.setCreatedAt(LocalDateTime.now());
        post.setStatusPost(Utils.StatusPost.WAIT);
        post.setStatus(Utils.Status.ACTIVE);
        return Post.toDTO(postRepository.save(post));
    }

    public PostDTO getById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return Post.toDTO(optionalPost.get());
    }

    public PostDTO update(Long id, PostDTO postDTO, Long uid) {
        PostDTO.validate(postDTO);
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent()) {
            throw new IllegalArgumentException("Post not found");
        }

        Post post = postOptional.get();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setPrice(postDTO.getPrice());
        post.setLocation(postDTO.getLocation());
        post.setCategoryId(postDTO.getCategoryId());
        return Post.toDTO(postRepository.save(post));
    }

    public PostDTO delete(Long id, Long uid) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("post not found");
        }
        Post post = optionalPost.get();
        post.setStatus(Utils.Status.IN_ACTIVE);
        post.setUpdateBy(uid);
        post.setUpdatedAt(LocalDateTime.now());
        post = postRepository.save(post);
        return Post.toDTO(post);
    }

    public PostDTO restore(Long id, Long uid) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("post not found");
        }
        Post post = optionalPost.get();
        post.setStatus(Utils.Status.ACTIVE);
        post.setUpdateBy(uid);
        post.setUpdatedAt(LocalDateTime.now());
        post = postRepository.save(post);
        return Post.toDTO(post);
    }


    public Object changeStatusPost(Map<String, Object> payload) {
        Integer status = (Integer) payload.get("checked");
        Long id = Long.valueOf(payload.get("id").toString());
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("post not found");
        }

        Post post = optionalPost.get();
        post.setStatusPost(status);
        post = postRepository.save(post);
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("id",post.getUserId());
        Map<String,Object>userMaper =market_authClient.getUserById(objectMap);

        String message = status == Utils.StatusPost.IN_ACTIVE
                ? " đã bị từ chối" : " đã được phê duyệt";
        userMaper.put("message",message);
        userMaper.put("title",post.getTitle());

        kafkaTemplate.send("mail-change-status-post-topic", userMaper);

        return Post.toDTO(post);
    }


}
