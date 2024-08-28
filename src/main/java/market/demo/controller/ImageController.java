package market.demo.controller;

import lombok.RequiredArgsConstructor;
import market.demo.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestHeader("uid") Long uid,
            @RequestParam(value = "file", required = false) List<MultipartFile> file,
            @RequestParam(value = "image", required = false) List<Long> images,
            @RequestParam("postId") Long postId) throws IOException {
        return ResponseEntity.ok(imageService.create(file, images, postId, uid));
    }

    @GetMapping("/get-by-post-id/{id}")
    public ResponseEntity<?> getAllByRoomId(@RequestHeader("uid") Long cid,
                                            @PathVariable("id") Long postId) {
        return ResponseEntity.ok(imageService.getAllByPostId(postId, cid));
    }
}
