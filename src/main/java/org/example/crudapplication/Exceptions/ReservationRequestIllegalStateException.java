package org.example.crudapplication.Exceptions;

public class ReservationRequestIllegalStateException extends RuntimeException {

    public ReservationRequestIllegalStateException(String message) {

        super(message);
                // У класса RuntimeException (и у его родителя Exception) внутри уже заложено поле
                // для хранения текста ошибки (message) и методы для его получения (например, getMessage()).
    }
}
