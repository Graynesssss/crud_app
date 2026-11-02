package org.example.crudapplication.ExceptionHandler;

import org.example.crudapplication.Exceptions.ReservationNotFoundException;
import org.example.crudapplication.Exceptions.ReservationRequestIllegalArgumentException;
import org.example.crudapplication.Exceptions.ReservationRequestIllegalStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReservationNotFound(ReservationNotFoundException ex){

        var errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), // 404
                "Not Found",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ReservationRequestIllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleReservationIllegalArgument(ReservationRequestIllegalArgumentException ex){

        var errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // 400
                "Bad_Request",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ReservationRequestIllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleReservationIllegalState(ReservationRequestIllegalStateException ex){

        var errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(), // 409
                "Conflict",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
