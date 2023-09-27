package com.ijimenez.junit.junit5_app.eceptions;

public class DineroInsuficienteEception extends RuntimeException{
    public DineroInsuficienteEception(String message) {
        super(message);
    }
}
