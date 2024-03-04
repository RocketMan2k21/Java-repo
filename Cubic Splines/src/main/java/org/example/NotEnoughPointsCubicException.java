package org.example;

public class NotEnoughPointsCubicException extends Exception {
    NotEnoughPointsCubicException(){
        super("Потрібно вести не менше трьох точок");
    }
}
