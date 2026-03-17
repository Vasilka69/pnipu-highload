package com.example.lab3.service;

import com.example.lab3.dto.QueueTaskDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisQueueService {

    private static final String QUEUE_KEY = "lab3:task-queue";
    private static final String PROCESSED_KEY = "lab3:processed-tasks";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${queue.processed.history-limit}")
    private long processedHistoryLimit;

    public QueueTaskDto enqueue(String payload) {
        QueueTaskDto task = new QueueTaskDto(
                UUID.randomUUID().toString(),
                payload,
                System.currentTimeMillis(),
                -1L
        );
        redisTemplate.opsForList().rightPush(QUEUE_KEY, toJson(task));
        return task;
    }

    public QueueTaskDto poll() {
        String raw = redisTemplate.opsForList().leftPop(QUEUE_KEY);
        if (raw == null) {
            return null;
        }
        return fromJson(raw);
    }

    public void saveProcessed(QueueTaskDto task) {
        QueueTaskDto processed = new QueueTaskDto(task.id(), task.payload(), task.createdAt(), System.currentTimeMillis());
        redisTemplate.opsForList().leftPush(PROCESSED_KEY, toJson(processed));
        redisTemplate.opsForList().trim(PROCESSED_KEY, 0, Math.max(0, processedHistoryLimit - 1));
    }

    public List<QueueTaskDto> processed(int limit) {
        List<String> raw = redisTemplate.opsForList().range(PROCESSED_KEY, 0, Math.max(limit, 1) - 1L);
        if (raw == null || raw.isEmpty()) {
            return Collections.emptyList();
        }
        return raw.stream().map(this::fromJson).toList();
    }

    public long pendingSize() {
        Long size = redisTemplate.opsForList().size(QUEUE_KEY);
        return size == null ? 0L : size;
    }

    public long processedSize() {
        Long size = redisTemplate.opsForList().size(PROCESSED_KEY);
        return size == null ? 0L : size;
    }

    private String toJson(QueueTaskDto task) {
        try {
            return objectMapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize queue task", e);
        }
    }

    private QueueTaskDto fromJson(String raw) {
        try {
            return objectMapper.readValue(raw, QueueTaskDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to deserialize queue task", e);
        }
    }
}
