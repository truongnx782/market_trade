package market.demo.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import market.demo.base.BaseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse extends BaseDTO {
    private Long id;
    private String postCode;
    private String title;
    private String description;
    private BigDecimal price;
    private Long userId;
    private List<String> imageUrls ;
    private String location;
    private String fullNameUser;
    private String phoneNumber;
    private int statusPost;
    private int status;
}
