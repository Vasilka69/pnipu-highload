package com.example.lab1.controller;


import com.example.lab1.dto.CounterDto;
import com.example.lab1.utils.InstanceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/counter")
@RestController
public class CountingController {

    private final InstanceUtils instanceUtils;
    private final AtomicInteger counter = new AtomicInteger(0);


    @GetMapping
    public CounterDto incrementAndGet() {
        int counterValue = counter.incrementAndGet();
        log.info("Получен запрос на инкремент счетчика: значение {}, InstanceInfo = {}", counterValue, instanceUtils.getInstanceInfo());
        return new CounterDto(counterValue, instanceUtils.getInstanceInfo());
    }

    @GetMapping("/silent")
    public CounterDto get() {
        return new CounterDto(counter.get(), instanceUtils.getInstanceInfo());
    }

}
