package market.demo.repository;

import market.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByStatus(Integer active);

    @Query(value = "SELECT c FROM Category c WHERE" +
            " c.id = (SELECT MAX(c2.id) FROM Category c2 ) ")
    Optional<Category> findMaxId();

    List<Category> findAllByOrderByIdDesc();

    @Query(value = "SELECT u FROM Category u WHERE " +
            "(u.categoryCode LIKE %:search% OR u.categoryName LIKE %:search% ) " +
            "AND (:status IS NULL OR u.status = :status) " +
            "ORDER BY u.id DESC")
    Page<Category> search(@Param("search") String search,
                         @Param("status") Integer status,
                         Pageable pageable);}
