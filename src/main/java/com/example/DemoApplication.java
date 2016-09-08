package com.example;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping(path = "v2", produces = "application/json")
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping(value = "catalog")
    Object catalog() {
        return new InputStreamResource(getClass().getResourceAsStream("/catalog.json"));
    }

    @RequestMapping("service_instances/{id}")
    Object serviceInstance(@PathVariable String id, HttpServletRequest request) {
        logger.info("instance id: {}, method: {}", id, request.getMethod());
        return "{}";
    }

    @RequestMapping("service_instances/{instanceId}/service_bindings/{bindingId}")
    Object serviceBinding(@PathVariable String instanceId,
                          @PathVariable String bindingId,
                          HttpServletRequest request) {

        logger.info("instance: {}, binding: {}, method: {}", instanceId, bindingId, request.getMethod());
        Map<String, String> cred = new HashMap<>();
        cred.put("url", "http://blarblar");
        cred.put("username", "123");
        cred.put("password", "123");

        return Collections.singletonMap("credentials", cred);
    }
}
