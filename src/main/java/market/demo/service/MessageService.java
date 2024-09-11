package market.demo.service;

import lombok.RequiredArgsConstructor;
import market.demo.Util.Utils;
import market.demo.entity.Message;
import market.demo.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> getMessages(Long senderId, Long receiverId) {
        return messageRepository.findAllBySenderIdAndReceiverIdOrderByCreatedAt(senderId, receiverId);
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setCreateBy(senderId);
        message.setCreatedAt(LocalDateTime.now());
        message.setStatus(Utils.Status.ACTIVE);
        return messageRepository.save(message);
    }
}
