package com.capge.casestudy.searchservice.service.Feign;

import com.capge.casestudy.searchservice.dto.Flight;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="FLIGHT-SERVICE")
public interface FlightFeignService {
    @GetMapping("/flights")
    List<Flight> getAllFlights();
}
