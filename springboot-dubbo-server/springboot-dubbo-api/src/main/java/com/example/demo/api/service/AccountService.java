package com.example.demo.api.service;

/**
 *
 */
public interface AccountService {
    void addAmount(Long userId, Integer amount);

    void removeAmount(Long userId, Integer amount);
}
