package market.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import market.demo.base.BaseEntity;
import market.demo.dto.MessageDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "SenderId")
    private Long senderId;

    @Column(name = "Receiver")
    private Long receiverId;

    @Column(name = "Content")
    private String content;

    @Column(name = "Status")
    private int status;

    public static MessageDTO toDTO(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .createBy(message.getCreateBy())
                .updateBy(message.getUpdateBy())
                .status(message.getStatus())
                .build();
    }

    public static Message toEntity(MessageDTO message) {
        return Message.builder()
                .id(message.getId())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .createBy(message.getCreateBy())
                .updateBy(message.getUpdateBy())
                .status(message.getStatus())
                .build();
    }

}
