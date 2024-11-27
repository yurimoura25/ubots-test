package com.yuri.ubots_test.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class IdGenerator {

    private final static Map<String, Long> mapId = new ConcurrentHashMap<>();

    public static Long nextId(String table) {
        return mapId.compute(table, (k, v) -> mapId.getOrDefault(k, 0L)+1);
    }

}
