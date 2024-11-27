package com.yuri.ubots_test.model;

import lombok.Getter;

@Getter
public enum SupportTeam {
    CARDS("cards"),
    LOANS("loans"),
    OTHERS("others");

    private final String routingKey;

    SupportTeam(String routingKey) {
        this.routingKey = routingKey;
    }

}
