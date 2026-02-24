package com.example.lab2.controller;

import com.example.lab2.dto.CounterDto;
import com.example.lab2.service.RedisCounterService;
import com.example.lab2.utils.InstanceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/counter")
@RestController
public class CountingController {

    private final InstanceUtils instanceUtils;
    private final RedisCounterService redisCounterService;

    @GetMapping
    public CounterDto incrementAndGet() {
        int counterValue = redisCounterService.incrementAndGet();
        log.info("Получен запрос на инкремент счетчика: значение {}, InstanceInfo = {}", counterValue, instanceUtils.getInstanceInfo());
        return new CounterDto(counterValue, instanceUtils.getInstanceInfo());
    }

    @GetMapping("/silent")
    public CounterDto get() {
        return new CounterDto(redisCounterService.get(), instanceUtils.getInstanceInfo());
    }
}
