package market.demo.repository;

import jakarta.transaction.Transactional;
import market.demo.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {
    Optional<Follow> findFollowByFollowerIdAndFollowingId(Long uid, Long followingId);

    @Modifying
    @Transactional
    @Query(value = "delete from  Follow f where f.followerId=:followerId and f.followingId=:followingId")
    void deleteByFollowerIdAndFollowingId(@Param("followerId") Long followerId,@Param("followingId") Long followingId);

    List<Follow> getAllByFollowerId(Long id);
}
