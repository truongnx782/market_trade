//package market.demo.controller;
//
//import market.demo.service.ImageService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/image")
//public class ImageController {
//    private final ImageService imageService;
//
//    public ImageController(ImageService imageService) {
//        this.imageService = imageService;
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<?> create(
//            @RequestHeader("cid") Long cid,
//            @RequestParam(value = "file", required = false) List<MultipartFile> file,
//            @RequestParam(value = "image", required = false) List<Long> images,
//            @RequestParam("room") Long roomId,
//            @RequestParam("status") Integer status) throws IOException {
//        return ResponseEntity.ok(imageService.create(file, images, roomId, status, cid));
//    }
//
//    @GetMapping("/get-by-room-id/{id}")
//    public ResponseEntity<?> getAllByRoomId(@RequestHeader("cid") Long cid,
//                                            @PathVariable("id") Long RoomId) {
//        return ResponseEntity.ok(imageService.getAllByRoomId(RoomId, cid));
//    }
//}
