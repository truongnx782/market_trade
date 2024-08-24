package market.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PostDTO extends BaseDTO {
    private Long id;
    private String postCode;
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private Long userId;
    private Long categoryId;
    private int statusPost;
    private int status;
}
