package market.demo.controller;

import lombok.RequiredArgsConstructor;
import market.demo.dto.MessageDTO;
import market.demo.entity.Message;
import market.demo.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getMessages(senderId, receiverId));
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDTO messageRequest) {
        Message message = messageService.sendMessage(messageRequest.getSenderId(), messageRequest.getReceiverId(), messageRequest.getContent());
        return ResponseEntity.ok(message);
    }
}
