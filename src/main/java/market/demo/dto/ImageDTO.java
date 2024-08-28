package market.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseDTO;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ImageDTO extends BaseDTO {
    private Long id;

    private Long postId;

    private String url;

    private Integer state;

    private Integer status;

}
