package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            Optional<Account> newAccount = accountService.register(account);
            if (newAccount.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            else {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(newAccount.get());
            }
        }
        catch (DuplicateUsernameException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Optional<Account> retrievedAccount = accountService.login(account);
        if (retrievedAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(retrievedAccount.get());
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Optional<Message> newMessage = messageService.createMessage(message);
        if (newMessage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(newMessage.get());
        }
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        Optional<Message> message = messageService.getMessageById(message_id);
        if (!message.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(message.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id) {
        int rowsAffected = messageService.deleteMessageById(message_id);
        if (rowsAffected == 0) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(rowsAffected);
        }
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int message_id, @RequestBody Message message) {
        int rowsUpdated = messageService.updateMessageById(message.getMessageText(), message_id);
        if (rowsUpdated == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(rowsUpdated);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int account_id) {
        List<Message> messages = messageService.getAllMessagesByAccountId(account_id);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
