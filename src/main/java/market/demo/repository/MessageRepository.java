package market.demo.repository;

import market.demo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "select m from Message m where (m.senderId=:senderId and m.receiverId=:receiverId ) " +
            "or ( m.senderId=:receiverId and m.receiverId=:senderId ) " +
            "and m.status=1")
    List<Message> findAllBySenderIdAndReceiverIdOrderByCreatedAt(@Param("senderId") Long senderId,@Param("receiverId") Long receiverId);
}
