package ru.sunoplyaandesin.simplemessenger.exception;


public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
        super("Wrong password");
    }
}