package com.yuri.ubots_test.service;

import com.yuri.ubots_test.dto.SupportRequestDTO;
import com.yuri.ubots_test.dto.SupportRequestResponseDTO;
import com.yuri.ubots_test.model.SupportRequest;
import com.yuri.ubots_test.model.SupportTeam;
import com.yuri.ubots_test.repository.AttendantRepository;
import com.yuri.ubots_test.repository.SupportRequestRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SupportRequestService {

    @Autowired
    private AttendantRepository attendantRepository;
    @Autowired
    private SupportRequestRepository supportRequestRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public SupportRequestResponseDTO requestSupport(SupportRequestDTO supportRequestDTO) {
        var supportRequest = SupportRequest.builder().subject(supportRequestDTO.subject()).message(supportRequestDTO.message()).build();

        supportRequestRepository.save(supportRequest);

        var supportTeam = getSupportTeamBySubject(supportRequest.getSubject());

        rabbitTemplate.convertAndSend("requests-ex", supportTeam.getRoutingKey(), supportRequest);

        return new SupportRequestResponseDTO(supportRequest.getPublicId(), supportRequest.getSubject(), supportRequest.getMessage(), supportRequest.getRequestedAt(), supportRequest.getUpdatedAt(), supportRequest.getId());
    }


    public List<SupportRequest> findAll() {
        return supportRequestRepository.findAll();
    }

    public void deleteAll() {
        supportRequestRepository.deleteAll();
    }

    public SupportTeam getSupportTeamBySubject(String subject) {
        return switch (subject) {
            case "Problemas com cartão" -> SupportTeam.CARDS;
            case "contratação de empréstimo" -> SupportTeam.LOANS;
            default -> SupportTeam.OTHERS;
        };
    }

}
