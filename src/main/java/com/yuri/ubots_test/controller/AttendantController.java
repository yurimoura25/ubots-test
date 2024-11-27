package com.yuri.ubots_test.controller;

import com.yuri.ubots_test.dto.AttendSupportDTO;
import com.yuri.ubots_test.dto.AttendantDTO;
import com.yuri.ubots_test.service.AttendantService;
import com.yuri.ubots_test.service.SupportRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InvalidObjectException;

@RestController
@RequestMapping("/attendant")
public class AttendantController {


    @Autowired
    private AttendantService attendantService;

    @PostMapping
    public ResponseEntity<?> saveAttendant(@RequestBody AttendantDTO attendantDTO) throws InvalidObjectException {
        var attendant = attendantService.saveAttendant(attendantDTO);

        return ResponseEntity.ok(attendant);

    }

    @PostMapping("/attend")
    public ResponseEntity<?> attendSupportRequest(@RequestBody AttendSupportDTO attendSupportDTO) throws InvalidObjectException {
        var supportRequest = attendantService.attendSupport(attendSupportDTO.attendantId());

        if(supportRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(supportRequest);

    }
}
