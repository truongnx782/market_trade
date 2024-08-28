package market.demo.repository;

import market.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query(value = "SELECT u FROM Post u WHERE " +
            "(u.title LIKE %:search% OR u.description LIKE %:search% ) " +
            "AND (:statusPost IS NULL OR u.statusPost = :statusPost) " +
            "ORDER BY u.id DESC")
    Page<Post> search(@Param("search") String search,
                      @Param("statusPost") Integer statusPost,
                           Pageable pageable);

    @Query(value = "SELECT u FROM Post u WHERE " +
            "(u.title LIKE %:search% OR u.description LIKE %:search% ) " +
            "AND u.userId=:uid " +
            "AND (:status IS NULL OR u.status = :status) " +
            "ORDER BY u.id DESC")
    Page<Post> searchByUid(@Param("search") String search,
                           @Param("status") Integer status,
                           Pageable pageable,
                           @Param("uid") Long uid);

    @Query(value = "SELECT p FROM Post p WHERE " +
            "p.id = (SELECT MAX(p2.id) FROM Post p2 ) " )
    Optional<Post> findMaxId();

    List<Post> findTop20ByStatusAndStatusPostOrderByCreatedAtDesc(Integer ACTIVE, Integer active);

}
