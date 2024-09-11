package market.demo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MessageDTO extends BaseDTO {
    private Long id;

    private Long senderId;

    private Long receiverId;

    private String content;

    private int status;

}
