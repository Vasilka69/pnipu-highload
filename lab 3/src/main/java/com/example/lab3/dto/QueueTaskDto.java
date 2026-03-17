package com.example.lab3.dto;

public record QueueTaskDto(String id, String payload, long createdAt, long processedAt) {
}
