package org.example.crudapplication.Exceptions;

public class ReservationRequestIllegalArgumentException extends RuntimeException {

    public ReservationRequestIllegalArgumentException(String message) {

        super(message);
                // У класса RuntimeException (и у его родителя Exception) внутри уже заложено поле
                // для хранения текста ошибки (message) и методы для его получения (например, getMessage()).
    }
}
