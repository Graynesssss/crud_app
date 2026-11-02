package org.example.crudapplication.availability;

import lombok.extern.slf4j.Slf4j;
import org.example.crudapplication.Exceptions.ReservationRequestIllegalArgumentException;
import org.example.crudapplication.ReservationRepo;
import org.example.crudapplication.ReservationStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ReservationAvailabilityService {

    private final ReservationRepo reservationRepo;

    public ReservationAvailabilityService(ReservationRepo reservationRepo){
        this.reservationRepo = reservationRepo;
    }

    public boolean isReservationAvailable(Long roomId, LocalDate startDate,LocalDate endDate){

        if(!endDate.isAfter(startDate)) {throw new ReservationRequestIllegalArgumentException
                ("Start date must be 1 day earlier than endDate");
        }

        List<Long> conflictIds = reservationRepo.findConflictReservationIds
                (roomId, startDate,endDate, ReservationStatus.APPROVED);

        log.info("Conflict with ids={}",conflictIds);
        if(!conflictIds.isEmpty()){return false;};

        log.info("Reservation is available");
        return true;
    }
}
