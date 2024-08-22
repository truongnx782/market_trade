package market.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.demo.dto.PostDTO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "PostCode")
    private String postCode;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "StatusPost")
    private int statusPost;

    @Column(name = "Status")
    private int status;

    public static Post toEntity(PostDTO postDTO){
        return Post.builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .description(postDTO.getDescription())
                .userId(postDTO.getUserId())
                .createdAt(postDTO.getCreatedAt())
                .updatedAt(postDTO.getUpdatedAt())
                .statusPost(postDTO.getStatusPost())
                .status(postDTO.getStatus())
                .build();
    }

    public static PostDTO toDTO(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .userId(post.getUserId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .statusPost(post.getStatusPost())
                .status(post.getStatus())
                .build();
    }

}
