package com.yuri.ubots_test.model;

import com.yuri.ubots_test.util.IdGenerator;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Attendant implements Entity{

    private final Long id = IdGenerator.nextId("attendant");
    private String name;
    private SupportTeam team;
    private List<SupportRequest> supportRequestList;

}
