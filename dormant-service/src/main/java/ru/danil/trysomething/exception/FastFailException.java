package ru.danil.trysomething.exception;

public class FastFailException extends RuntimeException{
    public FastFailException(String message){
        super(message);
    }
}
