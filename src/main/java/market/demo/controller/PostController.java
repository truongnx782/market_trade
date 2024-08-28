package market.demo.controller;

import lombok.RequiredArgsConstructor;
import market.demo.dto.PostDTO;
import market.demo.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/search")
    public ResponseEntity<?> search(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(postService.search(payload));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/change-status-post")
    public ResponseEntity<?> changeStatusPost(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(postService.changeStatusPost(payload));
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchPostList(@RequestHeader("uid")Long uid,
                                    @RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(postService.searchPostList(payload,uid));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllByStatusActive() {
        return ResponseEntity.ok(postService.getAllByStatusActive());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader("uid")Long uid,
                                    @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.create(postDTO,uid));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> create(@PathVariable("id") Long id,
                                    @RequestHeader("uid")Long uid,
                                    @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.update(id,postDTO,uid));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getById(id));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@RequestHeader("uid") Long uid,
                                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.delete(id, uid));
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<?> restore(@RequestHeader("uid") Long uid,
                                     @PathVariable("id") Long id) {
        return ResponseEntity.ok(postService.restore(id, uid));
    }
}
