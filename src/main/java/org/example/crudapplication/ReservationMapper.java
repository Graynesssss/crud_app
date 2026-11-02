package org.example.crudapplication;

import org.example.crudapplication.ReservationResponseModel;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponseModel toDomain(
            ReservationEntity reservation
    ) {
        return new ReservationResponseModel(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getRoomId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getStatus()
        );
    }

    public ReservationEntity toEntity(
            ReservationRequestModel reservation
    ) {
        return new ReservationEntity(
                reservation.id(),
                reservation.userId(),
                reservation.roomId(),
                reservation.startDate(),
                reservation.endDate(),
                null
        );
    }
}