package org.example;

public class NotEnoughPointsLinearException extends Exception {
    NotEnoughPointsLinearException(){
        super("Потрібно вести не менше двох точок");
    }
}
