package com.app.todolist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String id;
    private String name;
    private String state;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private UserDto user;
}
