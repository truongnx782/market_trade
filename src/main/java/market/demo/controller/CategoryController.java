package market.demo.controller;

import lombok.RequiredArgsConstructor;
import market.demo.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/search")
    public ResponseEntity<?> search(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(categoryRepository.search(payload));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<?> create(@RequestHeader("uid") Long uid,
                                    @RequestParam("categoryName") String categoryName,
                                    @RequestParam(value = "files") List<MultipartFile> file) {
        return ResponseEntity.ok(categoryRepository.ceate(categoryName, file, uid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id,
                                     @RequestHeader("uid") Long uid) {
        return ResponseEntity.ok(categoryRepository.getById(id, uid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @RequestHeader("uid") Long uid,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestParam(value = "url", required = false) String url) throws IOException {
        return ResponseEntity.ok(categoryRepository.update(id, categoryName, files, url, uid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader("uid") Long uid,
                                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryRepository.delete(id, uid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/restore/{id}")
    public ResponseEntity<?> restore(@RequestHeader("uid") Long uid,
                                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryRepository.restore(id, uid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/upload")
    public ResponseEntity<?> uploadFile(@RequestHeader("uid") Long uid,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(categoryRepository.importExcel(file, uid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/template")
    public ResponseEntity<?> downloadTemplate() {
        return ResponseEntity.ok(categoryRepository.exportTemplate());

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/export")
    public ResponseEntity<?> export(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(categoryRepository.exportData(payload));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryRepository.getAll());
    }

}
