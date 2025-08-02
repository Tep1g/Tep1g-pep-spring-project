package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE message m SET m.messageText=:newMessageText WHERE m.messageId=:id", nativeQuery = true)
    int updateMessageByMessageId(@Param("newMessageText") String newMessageText, @Param("id") Integer id);

    @Transactional
    int deleteByMessageId(Integer messageId);

    List<Message> getMessagesByPostedBy(Integer postedBy);
}
