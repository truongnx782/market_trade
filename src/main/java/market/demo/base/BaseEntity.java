package market.demo.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class BaseEntity {
    @Column(name = "CreatedAt", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;

    @Column(name = "CreateBy")
    private Long createBy;

    @Column(name = "UpdateBy")
    private Long updateBy;
}
