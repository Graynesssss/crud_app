package org.example.crudapplication.availability;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reservation/availability")
public class ReservationAvailabilityController {

    private final ReservationAvailabilityService availabilityService;

    public ReservationAvailabilityController(ReservationAvailabilityService availabilityService){
        this.availabilityService = availabilityService;
    }

    @PostMapping("/check")
    public ResponseEntity<CheckAvailabilityResponse> checkAvailability
            (@RequestBody @Valid CheckAvailabilityRequest checkAvailabilityRequest){

        log.info("Called checkAvailability request={}",checkAvailabilityRequest);
        var isAvailable = availabilityService.isReservationAvailable
                (checkAvailabilityRequest.roomId()
                        ,checkAvailabilityRequest.startDate()
                        ,checkAvailabilityRequest.endDate());

        var message = isAvailable ? "Reservation is available" : "Reservation is not available";

        var status = isAvailable ? AvailabilityStatus.AVAILABLE : AvailabilityStatus.RESERVED;

        return ResponseEntity.ok(new CheckAvailabilityResponse(message,status));
    }
}
