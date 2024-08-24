package market.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseEntity;
import market.demo.dto.CategoryDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "CategoryCode", nullable = false)
    private String categoryCode;

    @Column(name = "CategoryName", nullable = false)
    private String categoryName;

    @Column(name = "Url", nullable = false)
    private String url;

    @Column(name = "Status", nullable = false)
    private int status;

    public static CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .categoryCode(category.getCategoryCode())
                .categoryName(category.getCategoryName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .createBy(category.getCreateBy())
                .updateBy(category.getUpdateBy())
                .url(category.getUrl())
                .status(category.getStatus())
                .build();
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .id(categoryDTO.getId())
                .categoryCode(categoryDTO.getCategoryCode())
                .categoryName(categoryDTO.getCategoryName())
                .createdAt(categoryDTO.getCreatedAt())
                .updatedAt(categoryDTO.getUpdatedAt())
                .createBy(categoryDTO.getCreateBy())
                .updateBy(categoryDTO.getUpdateBy())
                .url(categoryDTO.getUrl())
                .status(categoryDTO.getStatus())
                .build();
    }
}
