package com.example.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        if (!(messageText.isBlank() && (messageText.length() < 255))) {
            try {
                newMessage = messageRepository.save(message);
            }
            // Handle non-existent posted_by user
            catch (ConstraintViolationException exception) {}
        }
        return Optional.ofNullable(newMessage);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int id) {
        return messageRepository.findById(id);
    }

    public boolean deleteMessageById(int id) {
        try {
            messageRepository.deleteById(id);
            return true;
        }
        catch (EmptyResultDataAccessException exception) {
            return false;
        }
    }

    public int updateMessageById(Message message) {
        return messageRepository.updateMessageById(message.getMessageText(), message.getMessageId());
    }

    public List<Message> getAllMessagesByAccountId(int accountId) {
        return messageRepository.getMessagesByAccountId(accountId);
    }
}
