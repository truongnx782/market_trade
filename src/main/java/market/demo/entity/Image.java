package market.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseEntity;
import market.demo.dto.CategoryDTO;
import market.demo.dto.ImageDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "PostId", nullable = false)
    private Long postId;

    @Column(name = "Url", nullable = false)
    private String url;

    @Column(name = "State",nullable = false)
    private Integer state;

    @Column(name = "Status", nullable = false)
    private Integer status;

    public static ImageDTO toDTO(Image image) {
        return ImageDTO.builder()
                .id(image.getId())
                .postId(image.getPostId())
                .url(image.getUrl())
                .createdAt(image.getCreatedAt())
                .updatedAt(image.getUpdatedAt())
                .createBy(image.getCreateBy())
                .updateBy(image.getUpdateBy())
                .state(image.getState())
                .status(image.getStatus())
                .build();
    }

    public static Image toEntity(ImageDTO image) {
        return Image.builder()
                .id(image.getId())
                .postId(image.getPostId())
                .url(image.getUrl())
                .createdAt(image.getCreatedAt())
                .updatedAt(image.getUpdatedAt())
                .createBy(image.getCreateBy())
                .updateBy(image.getUpdateBy())
                .state(image.getState())
                .status(image.getStatus())
                .build();
    }
}
