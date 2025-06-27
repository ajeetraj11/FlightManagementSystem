package com.capge.casestudy.checkinservice.controller;

import com.capge.casestudy.checkinservice.dto.PassengerDTO;
import com.capge.casestudy.checkinservice.entity.CheckIn;
import com.capge.casestudy.checkinservice.service.CheckInService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("checkin")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    @PostMapping
    public CheckIn saveCheckIn(@Valid @RequestBody PassengerDTO passengerDTO){
       return checkInService.saveCheckIn(passengerDTO);
    }
    @GetMapping("/{pnr}")
    public CheckIn getCheckInByPNR(@PathVariable String pnr) {
        return checkInService.getCheckInByPNR(pnr);
    }


}
