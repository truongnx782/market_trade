package market.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseEntity;
import market.demo.dto.FollowDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Follow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "FollowerId")
    private Long followerId;

    @Column(name = "FollowingId ")
    private Long followingId ;

    @Column(name = "Status", nullable = false)
    private int status;

    public static FollowDTO toDTO (Follow follow){
        return FollowDTO.builder()
                .id(follow.getId())
                .followerId(follow.getFollowerId())
                .followingId(follow.getFollowingId())
                .status(follow.getStatus())
                .createBy(follow.getCreateBy())
                .updateBy(follow.getUpdateBy())
                .createdAt(follow.getCreatedAt())
                .updatedAt(follow.getUpdatedAt())
                .build();
    }

    public static Follow toEntity (FollowDTO follow){
        return Follow.builder()
                .id(follow.getId())
                .followerId(follow.getFollowerId())
                .followingId(follow.getFollowingId())
                .status(follow.getStatus())
                .createBy(follow.getCreateBy())
                .updateBy(follow.getUpdateBy())
                .createdAt(follow.getCreatedAt())
                .updatedAt(follow.getUpdatedAt())
                .build();
    }
}
