package market.demo.service;

import lombok.RequiredArgsConstructor;
import market.demo.Util.MapperUtils;
import market.demo.Util.Utils;
import market.demo.dto.CategoryDTO;
import market.demo.entity.Category;
import market.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<CategoryDTO> getAllByStatusActive() {
        List<Category> categoryList = categoryRepository.findAllByStatus(Utils.Status.ACTIVE);
        return categoryList.stream().map(Category::toDTO).collect(Collectors.toList());
    }

    public CategoryDTO ceate(CategoryDTO categoryDTO, Long uid) {
        Category  category = MapperUtils.mapCommonFields(categoryDTO,Category.class);
        Optional<Category> maxIdSP = categoryRepository.findMaxId();
        Long maxId = maxIdSP.isPresent() ? maxIdSP.get().getId()+1 : 1;
        category.setCategoryCode("DM"+maxId);
        category.setCreateBy(uid);
        category.setCreatedAt(LocalDateTime.now());
        category.setStatus(Utils.Status.ACTIVE);
        category = categoryRepository.save(category);
        return Category.toDTO(category);
    }
}
