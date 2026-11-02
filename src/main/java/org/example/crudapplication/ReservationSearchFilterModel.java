package org.example.crudapplication;

public record ReservationSearchFilterModel(
        Long roomId,
        Long userId,
        Integer pageSize,
        Integer pageNumber
) {
}
