package market.demo.controller;

import lombok.RequiredArgsConstructor;
import market.demo.dto.CategoryDTO;
import market.demo.entity.Category;
import market.demo.repository.CategoryRepository;
import market.demo.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader("uid")Long uid,
                                    @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(categoryRepository.ceate(categoryDTO,uid));
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAllByStatusActive(){
        return ResponseEntity.ok(categoryRepository.getAllByStatusActive());
    }
}
