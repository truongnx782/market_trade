package market.demo.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import market.demo.dto.PostDTO;
import market.demo.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(postService.search(payload));
    }

    @GetMapping("getByUserId/{id}")
    public ResponseEntity<?> getAllByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postService.getAllByUserId(userId));
    }

    //    @PreAuthorize("hasRole('USER')")
    @GetMapping("getAll")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        kafkaTemplate.send("auth-getUserId", token);
        System.out.println("haha");
        return ResponseEntity.ok(postService.getAll());
    }

    @KafkaListener(topics = "auth-postUserId", groupId = "trade-group")
    public void listenForMailRegister(Long message) throws MessagingException {
            System.out.println(message); // In ra giá trị Long
    }



    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(postService.create(postDTO));
    }
}
