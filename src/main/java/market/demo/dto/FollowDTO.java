package market.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FollowDTO extends BaseDTO {
    private Long id;
    private Long followerId;
    private Long followingId ;
    private int status;
}
