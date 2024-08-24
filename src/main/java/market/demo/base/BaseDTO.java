package market.demo.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class BaseDTO {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createBy;
    private Long updateBy;
}
