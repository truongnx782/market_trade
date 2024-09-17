package market.demo.controller;

import lombok.RequiredArgsConstructor;
import market.demo.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(followService.getAll());
    }

    @GetMapping("/getAllByFollowerId/{id}")
    public ResponseEntity<?> getAllByFollowerId(@PathVariable("id")Long id){
        return ResponseEntity.ok(followService.getAllByFollowerId(id));
    }

    @GetMapping("/create")
    public ResponseEntity<?>create(@RequestHeader("uid") Long uid,
                                   @RequestParam ("followingId") Long followingId){
        return ResponseEntity.ok(followService.create(uid,followingId));
    }

    @GetMapping("/delete")
    public ResponseEntity<?>delete(@RequestHeader("uid") Long uid,
                                   @RequestParam ("followingId") Long followingId){
        return ResponseEntity.ok(followService.delete(uid,followingId));
    }

}
