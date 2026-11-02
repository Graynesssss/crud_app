package org.example.crudapplication.availability;

public record CheckAvailabilityResponse(
        String message,
        AvailabilityStatus status
) {
}
