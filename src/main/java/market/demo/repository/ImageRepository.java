package market.demo.repository;

import market.demo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findAllByStatus(Integer active);
}
