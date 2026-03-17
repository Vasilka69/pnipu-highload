package com.example.lab3.service;

import com.example.lab3.dto.QueueTaskDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueConsumerService {

    private final RedisQueueService redisQueueService;

    @Value("${queue.consumer.processing-delay-ms}")
    private long processingDelayMs;

    @Scheduled(fixedDelayString = "${queue.consumer.poll-delay-ms}")
    public void consumeOne() {
        QueueTaskDto task = redisQueueService.poll();
        if (task == null) {
            return;
        }

        try {
            Thread.sleep(Math.max(0, processingDelayMs));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        redisQueueService.saveProcessed(task);
        log.info("Processed queue task: id={}, payload={}", task.id(), task.payload());
    }
}
