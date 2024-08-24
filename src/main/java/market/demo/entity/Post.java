package market.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseEntity;
import market.demo.dto.PostDTO;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "PostCode",nullable = false)
    private String postCode;

    @Column(name = "Title",nullable = false)
    private String title;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Location")
    private String location;

    @Column(name = "UserId",nullable = false)
    private Long userId;

    @Column(name = "CategoryId",nullable = false)
    private Long categoryId;

    @Column(name = "StatusPost",nullable = false)
    private int statusPost;

    @Column(name = "Status",nullable = false)
    private int status;

    public static Post toEntity(PostDTO postDTO){
        return Post.builder()
                .id(postDTO.getId())
                .postCode(postDTO.getPostCode())
                .title(postDTO.getTitle())
                .description(postDTO.getDescription())
                .price(postDTO.getPrice())
                .location(postDTO.getLocation())
                .userId(postDTO.getUserId())
                .createdAt(postDTO.getCreatedAt())
                .updatedAt(postDTO.getUpdatedAt())
                .createBy(postDTO.getCreateBy())
                .updateBy(postDTO.getUpdateBy())
                .statusPost(postDTO.getStatusPost())
                .status(postDTO.getStatus())
                .build();
    }

    public static PostDTO toDTO(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .postCode(post.getPostCode())
                .title(post.getTitle())
                .description(post.getDescription())
                .price(post.getPrice())
                .location(post.getLocation())
                .userId(post.getUserId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .createBy(post.getCreateBy())
                .updateBy(post.getUpdateBy())
                .statusPost(post.getStatusPost())
                .status(post.getStatus())
                .build();
    }

}
