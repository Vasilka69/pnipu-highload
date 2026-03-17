package com.example.lab3.service;

import com.example.lab3.dto.QueueTaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class QueueProducerService {

    private final RedisQueueService redisQueueService;

    public List<QueueTaskDto> produceGenerated(int count, long minDelayMs, long maxDelayMs) {
        List<QueueTaskDto> produced = new ArrayList<>();
        int safeCount = Math.max(count, 0);
        for (int i = 0; i < safeCount; i++) {
            QueueTaskDto task = redisQueueService.enqueue("generated-message-" + (i + 1));
            produced.add(task);
            sleepRandom(minDelayMs, maxDelayMs);
        }
        return produced;
    }

    public List<QueueTaskDto> produceFromResource(String resourceName, long minDelayMs, long maxDelayMs) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource(resourceName).getInputStream(), StandardCharsets.UTF_8))) {
            List<QueueTaskDto> produced = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                produced.add(redisQueueService.enqueue(line));
                sleepRandom(minDelayMs, maxDelayMs);
            }
            return produced;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to produce tasks from resource: " + resourceName, e);
        }
    }

    private void sleepRandom(long minDelayMs, long maxDelayMs) {
        long min = Math.max(minDelayMs, 0);
        long max = Math.max(maxDelayMs, min);
        long delay = ThreadLocalRandom.current().nextLong(min, max + 1);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Producer interrupted", e);
        }
    }
}
