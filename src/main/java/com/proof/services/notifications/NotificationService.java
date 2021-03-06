package com.proof.services.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proof.api.controllers.clients.orders.requests.CreateOrderRequestDto;
import com.proof.api.controllers.clients.orders.requests.UpdateOrderRequestDto;
import com.proof.persistence.entities.Event;
import com.proof.persistence.repositories.EventRepository;

import org.springframework.stereotype.Service;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * This notification service will handle event notifications to external systems. Currently it only stores events as
 * JSON in the database, but this same approach can be used to push notifications to an external message queue.
 */
@Log4j2
@Service
public class NotificationService {

    private final EventRepository eventRepository;

    public NotificationService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void notifyOrderCreate(String clientCode, CreateOrderRequestDto createOrderRequestDto) {
        sendEvent(new CreateOrderRequest(clientCode, createOrderRequestDto));
    }

    public void notifyOrderUpdate(String clientCode, String orderCode, UpdateOrderRequestDto updateOrderRequestDto) {
        sendEvent(new UpdateOrderRequest(clientCode, orderCode, updateOrderRequestDto));
    }

    private void sendEvent(Object request) {
        try {
            String eventData = new ObjectMapper().
                    writer().
                    withDefaultPrettyPrinter().
                    writeValueAsString(request);

            Event event = new Event();
            event.setData(eventData);
            event.setCreationDate(new Date());
            eventRepository.save(event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Data
    @AllArgsConstructor
    private static class CreateOrderRequest {
        String clientCode;
        CreateOrderRequestDto createOrderRequestDto;
    }

    @Data
    @AllArgsConstructor
    private static class UpdateOrderRequest {
        String clientCode;
        String orderCode;
        UpdateOrderRequestDto updateOrderRequestDto;
    }
}
