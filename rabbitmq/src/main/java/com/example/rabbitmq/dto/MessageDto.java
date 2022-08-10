package com.example.rabbitmq.dto;

import com.example.rabbitmq.service.rabbit.enm.TypeHandler;
import lombok.Data;

@Data
public class MessageDto {

    private byte[] payload;

    private TypeHandler typeHandler;
}
