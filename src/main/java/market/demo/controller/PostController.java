package market.demo.controller;

import lombok.RequiredArgsConstructor;
import market.demo.dto.PostDTO;
import market.demo.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/search-by-uid")
    public ResponseEntity<?> search(@RequestHeader("uid")Long uid,
                                    @RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(postService.search(payload,uid));
    }

    @GetMapping("/getByUserId/{id}")
    public ResponseEntity<?> getAllByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postService.getAllByUserId(userId));
    }

    //    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllByStatusActive() {
        return ResponseEntity.ok(postService.getAllByStatusActive());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.create(postDTO));
    }
}
