package com.yuri.ubots_test;

import com.yuri.ubots_test.dto.AttendantDTO;
import com.yuri.ubots_test.dto.SupportRequestDTO;
import com.yuri.ubots_test.model.SupportRequest;
import com.yuri.ubots_test.model.SupportTeam;
import com.yuri.ubots_test.service.AttendantService;
import com.yuri.ubots_test.service.SupportRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.InvalidObjectException;
import java.util.Optional;

@SpringBootTest
public class AttendantServiceTests {

    @Autowired
    private AttendantService attendantService;

    @Autowired
    private SupportRequestService supportRequestService;

    @Autowired
    private RabbitAdmin admin;

    @BeforeEach
    public void clearSupportRequests() {
        admin.purgeQueue("cards-requests");
        admin.purgeQueue("loans-requests");
        admin.purgeQueue("others-requests");
    }

    @Test
    public void shouldAttendToCards() throws InvalidObjectException {
        var attendant = attendantService.saveAttendant(new AttendantDTO("Ana", SupportTeam.CARDS));

        var supportRequest = supportRequestService.requestSupport(new SupportRequestDTO("Problemas com cartão", "Quero cancelar"));

        var attendedSupport = attendantService.attendSupport(attendant.getId());


        Assert.isTrue(attendedSupport.isPresent(), "Support Request not found on queue");

        Assert.isTrue(attendedSupport.get().getId().equals(supportRequest.id()), "Support Request is different than the requested one");

    }

    @Test
    public void shouldAttendToLoans() throws InvalidObjectException {
        var attendant = attendantService.saveAttendant(new AttendantDTO("Lucas", SupportTeam.LOANS));

        var supportRequest = supportRequestService.requestSupport(new SupportRequestDTO("contratação de empréstimo", "Quero contratar"));

        var attendedSupport = attendantService.attendSupport(attendant.getId());


        Assert.isTrue(attendedSupport.isPresent(), "Support Request not found on queue");

        Assert.isTrue(attendedSupport.get().getId().equals(supportRequest.id()), "Support Request is different than the requested one");

    }

    @Test
    public void shouldAttendToOthers() throws InvalidObjectException {
        var attendant = attendantService.saveAttendant(new AttendantDTO("Ana", SupportTeam.OTHERS));

        var supportRequest = supportRequestService.requestSupport(new SupportRequestDTO("Outros", "Problema"));

        var attendedSupport = attendantService.attendSupport(attendant.getId());


        Assert.isTrue(attendedSupport.isPresent(), "Support Request not found on queue");

        Assert.isTrue(attendedSupport.get().getId().equals(supportRequest.id()), "Support Request is different than the requested one");

    }


    @Test
    public void shouldNotAttendLimitExceeded() throws InvalidObjectException {
        var attendant = attendantService.saveAttendant(new AttendantDTO("Ana", SupportTeam.CARDS));
        supportRequestService.requestSupport(new SupportRequestDTO("Problemas com cartão", "Quero cancelar"));

        attendantService.attendSupport(attendant.getId());

        supportRequestService.requestSupport(new SupportRequestDTO("Problemas com cartão", "Quero cancelar"));

        attendantService.attendSupport(attendant.getId());

        supportRequestService.requestSupport(new SupportRequestDTO("Problemas com cartão", "Quero cancelar"));

        attendantService.attendSupport(attendant.getId());

        Optional<SupportRequest> supportRequest = Optional.empty();
        try {

            supportRequestService.requestSupport(new SupportRequestDTO("Problemas com cartão", "Quero cancelar"));
            supportRequest = attendantService.attendSupport(attendant.getId());
        } catch(InvalidObjectException ex) {
        }

        Assert.isTrue(supportRequest.isEmpty(), "Attendant should not exceed limit");



    }

}
