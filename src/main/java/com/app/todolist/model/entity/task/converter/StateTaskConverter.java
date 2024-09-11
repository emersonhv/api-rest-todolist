package com.app.todolist.model.entity.task.converter;

import com.app.todolist.model.entity.task.State;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StateTaskConverter implements AttributeConverter<State, String> {

    @Override
    public String convertToDatabaseColumn(State attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public State convertToEntityAttribute(String dbData) {
        return dbData != null ? State.findById(dbData) : State.NS;
    }
}
