package com.capge.casestudy.passengerservice.service;

import com.capge.casestudy.passengerservice.entity.Passenger;
import com.capge.casestudy.passengerservice.repository.PassengerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class PassengerRequestConsumer {
    @Autowired
    private PassengerService passengerService;

    @RabbitListener(queues = "queue.response.booking")
    public void passengerConsumer(Map<String, Object> payload) {
	   log.info("Received Passenger Booking Payload: {}", payload);
       passengerService.savePassenger(payload);
    }
}
