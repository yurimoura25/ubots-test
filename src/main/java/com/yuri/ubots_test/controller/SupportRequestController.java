package com.yuri.ubots_test.controller;


import com.yuri.ubots_test.dto.SupportRequestDTO;
import com.yuri.ubots_test.service.SupportRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/support-request")
public class SupportRequestController {

    private final SupportRequestService supportRequestService;

    @Autowired
    public SupportRequestController(SupportRequestService supportRequestService) {
        this.supportRequestService = supportRequestService;
    }

    @PostMapping
    public ResponseEntity<?> requestSupport(
            @RequestBody SupportRequestDTO supportRequestDTO
    ) {

        var supportRequestResponse = supportRequestService.requestSupport(supportRequestDTO);

        return ResponseEntity.ok(supportRequestResponse);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(supportRequestService.findAll());
    }


}
