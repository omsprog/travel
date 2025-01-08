package com.omsprog.tour_service.controller.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path="tours")
@AllArgsConstructor
public class TourController {
    @GetMapping
    public Map<String, String> getAll() {
        return Collections.singletonMap("message", "Testing service");
    }
}
