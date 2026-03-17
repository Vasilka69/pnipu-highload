package com.example.lab3.controller;

import com.example.lab3.dto.QueueEnqueueRequestDto;
import com.example.lab3.dto.QueueStatsDto;
import com.example.lab3.dto.QueueTaskDto;
import com.example.lab3.service.QueueProducerService;
import com.example.lab3.service.RedisQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/queue")
@RestController
public class QueueController {

    private final RedisQueueService redisQueueService;
    private final QueueProducerService queueProducerService;

    @PostMapping("/enqueue")
    public QueueTaskDto enqueue(@RequestBody QueueEnqueueRequestDto request) {
        return redisQueueService.enqueue(request.payload());
    }

    @PostMapping("/produce/generated")
    public List<QueueTaskDto> produceGenerated(
            @RequestParam(defaultValue = "20") int count,
            @RequestParam(defaultValue = "50") long minDelayMs,
            @RequestParam(defaultValue = "500") long maxDelayMs
    ) {
        return queueProducerService.produceGenerated(count, minDelayMs, maxDelayMs);
    }

    @PostMapping("/produce/from-file")
    public List<QueueTaskDto> produceFromFile(
            @RequestParam(defaultValue = "queue-input.txt") String resource,
            @RequestParam(defaultValue = "50") long minDelayMs,
            @RequestParam(defaultValue = "500") long maxDelayMs
    ) {
        return queueProducerService.produceFromResource(resource, minDelayMs, maxDelayMs);
    }

    @GetMapping("/processed")
    public List<QueueTaskDto> processed(@RequestParam(defaultValue = "20") int limit) {
        return redisQueueService.processed(limit);
    }

    @GetMapping("/stats")
    public QueueStatsDto stats() {
        return new QueueStatsDto(redisQueueService.pendingSize(), redisQueueService.processedSize());
    }
}
