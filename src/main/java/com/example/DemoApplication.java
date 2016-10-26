package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@SpringBootApplication
@RestController
@RequestMapping(path = "v2", produces = "application/json")
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Value("${VCAP_APPLICATION:}")
    String vcapApplication;

    String catalog;

    @PostConstruct
    void init() throws IOException {

        String guid;

        try {
            guid = Objects.toString(new ObjectMapper().readValue(vcapApplication, Map.class).get("application_id"));
        } catch (IOException e) {
            logger.error("VCAP_APPLICATION is not set properly, using fallback", e);
            guid = UUID.randomUUID().toString();
        }

        catalog = IOUtils.toString(getClass().getResourceAsStream("/catalog.json"), StandardCharsets.UTF_8)
                .replaceAll("\\{\\{GUID\\}\\}", guid);
    }

    @RequestMapping(value = "catalog")
    Object catalog() {
        return catalog;
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
