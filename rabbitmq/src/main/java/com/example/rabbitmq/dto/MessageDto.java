package com.example.rabbitmq.dto;

import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MessageDto {

    private byte[] payload;

    private TypeHandler typeHandler;

    private Map<String, String> headers = new HashMap<>();
}
