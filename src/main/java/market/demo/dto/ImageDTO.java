package market.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDTO {
    private Long id;

    private Long postId;

    private String url;

    private Integer state;

    private Integer status;

}
