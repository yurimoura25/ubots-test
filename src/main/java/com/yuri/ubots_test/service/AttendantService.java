package com.yuri.ubots_test.service;

import com.yuri.ubots_test.dto.AttendantDTO;
import com.yuri.ubots_test.messaging.ObjectConverter;
import com.yuri.ubots_test.model.Attendant;
import com.yuri.ubots_test.model.SupportRequest;
import com.yuri.ubots_test.repository.AttendantRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class AttendantService {

    @Autowired
    private AttendantRepository attendantRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectConverter objectConverter;

    public Attendant saveAttendant(AttendantDTO attendantDTO) {
        var attendant = Attendant.builder()
                .name(attendantDTO.name())
                .team(attendantDTO.team())
                .supportRequestList(new LinkedList<>())
                .build();
        attendantRepository.save(attendant);
        return attendant;
    }


    public Optional<SupportRequest> attendSupport(Long attendantId) throws InvalidObjectException {
        var optionalAttendant = attendantRepository.getById(attendantId);
        if (optionalAttendant.isEmpty()) {
            return Optional.empty();
        }

        var attendant = optionalAttendant.get();

        if (attendant.getSupportRequestList().size() < 3) {

            var message = consumeMessage(attendant.getTeam().name().toLowerCase());

            if (message.isEmpty()) {
                return Optional.empty();
            }

            var supportRequest = (SupportRequest) objectConverter.fromMessage(message.get());
            attendant.getSupportRequestList().add(supportRequest);
            return Optional.of(supportRequest);

        }

        throw new InvalidObjectException("Max number of support attend exceeded");

    }

    private Optional<Message> consumeMessage(String queueName) {
        Message message = rabbitTemplate.receive(queueName + "-requests");

        if (message == null) {
            return Optional.empty();
        }

        return Optional.of(message);
    }

    public void deleteAll() {
        attendantRepository.deleteAll();
    }
}
