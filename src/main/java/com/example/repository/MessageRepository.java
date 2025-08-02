package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Modifying
    @Query(value = "UPDATE message m SET m.messageText=:newMessageText WHERE m.messageId=:id", nativeQuery = true)
    int updateMessageByMessageId(@Param("newMessageText") String newMessageText, @Param("id") Integer id);

    int deleteByMessageId(@Param("id") Integer id);

    List<Message> getMessagesByPostedBy(Integer postedBy);
}
