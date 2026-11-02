package org.example.crudapplication;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseModel> getReservationById(
            @PathVariable("id") Long id
    ){
        log.info("Called getReservationById: id=" + id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.getReservationById(id));
        // return ResponseEntity.ok(reservationService.getReservationById(id))
        // Это эквивалетные операции.
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseModel>> getAllReservations(
            @RequestParam(name = "roomId",required = false) Long roomId,
            @RequestParam(name = "userId",required = false) Long userId,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "pageNumber",required = false) Integer pageNumber

    ){
        log.info("Called getAllResevations");
        var filter = new ReservationSearchFilterModel(
                roomId,
                userId,
                pageSize,
                pageNumber
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.searchAllByFilter(filter));
        // return ResponseEntity.ok(reservationService.searchAllByFilter(filter))
        // Это эквивалетные операции.
    }

    @PostMapping
    public ResponseEntity<ReservationResponseModel> CreateReservation(
            @RequestBody @Valid ReservationRequestModel reservationToCreate
    ){
        log.info("Create Reservation: reservationToCreate={}",reservationToCreate);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.createReservation(reservationToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseModel> UpdateReservation(
            @PathVariable("id") Long id,
            @RequestParam @Valid ReservationRequestModel reservationToUpdate
    ){
        log.info("Called UpdateReservation: id={},reservationToUpdate={}",id,reservationToUpdate);
        var update = reservationService.updateReservation(id,reservationToUpdate);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponseModel> DeleteReservation(
            @PathVariable("id") Long id
    ){
        log.info("Called DeleteReservation: id={}",id);
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<ReservationResponseModel> ApproveReservation(
            @PathVariable("id") Long id
    ){
        log.info("Approve Reservation: id={}",id);
        var approved = reservationService.approveReservation(id);
        return ResponseEntity.ok(approved);
    }
}
