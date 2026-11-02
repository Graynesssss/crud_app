package org.example.crudapplication;

import java.time.LocalDate;

public record ReservationResponseModel(
        Long id,                // ID, который сгенерировала база данных
        Long userId,            // Кто забронировал
        Long roomId,            // Какую комнату
        LocalDate startDate,    // Дата начала
        LocalDate endDate,      // Дата окончания
        ReservationStatus status // Актуальный статус (например, PENDING или APPROVED)
) {
}
