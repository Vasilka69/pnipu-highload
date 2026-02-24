package com.example.lab2.utils;

import com.example.lab2.dto.InstanceInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class InstanceUtils {

    @Value("${instance.name}")
    private String instanceName;

    @Value("${server.port}")
    private String serverPort;

    private InstanceInfo instanceInfo;

    @PostConstruct
    public void init() {
        instanceInfo = new InstanceInfo(instanceName, serverPort);
    }

    public InstanceInfo getInstanceInfo() {
        return instanceInfo;
    }
}
