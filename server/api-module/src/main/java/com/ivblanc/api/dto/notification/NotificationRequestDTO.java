package com.ivblanc.api.dto.notification;

import org.springframework.stereotype.Service;

import lombok.Data;


@Service
@Data
public class NotificationRequestDTO {

    private String registration_ids;
    private NotificationData notification;
    private NotificationData data;

}
