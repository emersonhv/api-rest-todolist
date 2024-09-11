package com.app.todolist.model.entity.task;

import lombok.Getter;

import java.util.HashMap;

public enum State {
    NO_RESOLVED(State.NO_RESOLVED_CODE, "No resuelto"),
    RESOLVED(State.RESOLVED_CODE, "Resuelto"),
    NS(State.NS_CODE, "Sin estado");

    private static final String NO_RESOLVED_CODE = "RES0";
    private static final String RESOLVED_CODE = "RES1";
    private static final String NS_CODE = "RES1";

    private static final HashMap<String, State> ENUM_MAP_BY_CODE = new HashMap<>(3);

    static {
        ENUM_MAP_BY_CODE.put(State.NO_RESOLVED_CODE, NO_RESOLVED);
        ENUM_MAP_BY_CODE.put(State.RESOLVED_CODE, RESOLVED);
        ENUM_MAP_BY_CODE.put(State.NS_CODE, NS);
    }
    @Getter
    private final String code;
    private final String description;

    State(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static State findById(String id) { return ENUM_MAP_BY_CODE.get(id);}




}
