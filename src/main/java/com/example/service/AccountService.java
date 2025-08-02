package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> register(Account account) throws DuplicateUsernameException{
        Account newAccount = null;
        String username = account.getUsername();
        if (!(username.isBlank()) && (username.length() >= 4)) {
            try {
                newAccount = accountRepository.save(account);
            }
            // Handle non-unique username
            catch (DataIntegrityViolationException exception) {
                throw new DuplicateUsernameException("Username is already taken");
            }
        }
        return Optional.ofNullable(newAccount);
    }

    public Optional<Account> login(Account account) {
        return Optional.ofNullable(accountRepository.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword()));
    }
}
