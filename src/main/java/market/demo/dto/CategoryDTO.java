package market.demo.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseDTO;

@Data
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public class CategoryDTO extends BaseDTO {
    private Long id;
    private String categoryCode;
    private String categoryName;
    private String url;
    private Integer status;
}
