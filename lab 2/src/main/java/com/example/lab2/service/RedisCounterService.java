package com.example.lab2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCounterService {

    private static final String COUNTER_KEY = "lab2:counter";
    private final StringRedisTemplate redisTemplate;

    public int incrementAndGet() {
        Long value = redisTemplate.opsForValue().increment(COUNTER_KEY);
        return value == null ? 0 : value.intValue();
    }

    public int get() {
        String value = redisTemplate.opsForValue().get(COUNTER_KEY);
        return value == null ? 0 : Integer.parseInt(value);
    }
}
