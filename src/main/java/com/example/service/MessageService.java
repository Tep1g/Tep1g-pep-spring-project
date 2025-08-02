package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Optional<Message> createMessage(Message message) {
        Message newMessage = null;
        String messageText = message.getMessageText();
        if (!(messageText.isBlank()) && (messageText.length() < 255)) {
            try {
                newMessage = messageRepository.save(message);
            }
            // Handle non-existent posted_by user
            catch (DataIntegrityViolationException exception) {}
        }
        return Optional.ofNullable(newMessage);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int id) {
        return messageRepository.findById(id);
    }

    public int deleteMessageById(int id) {
        return messageRepository.deleteByMessageId(id);
    }

    public int updateMessageById(String newMessageText, int messageId) {
        if ((!newMessageText.isEmpty()) && (newMessageText.length() <= 255)) {
            return messageRepository.updateMessageByMessageId(newMessageText, messageId);
        }
        return 0;
    }

    public List<Message> getAllMessagesByAccountId(int accountId) {
        return messageRepository.getMessagesByPostedBy(accountId);
    }
}
