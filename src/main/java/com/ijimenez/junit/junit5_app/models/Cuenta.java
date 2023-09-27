package com.ijimenez.junit.junit5_app.models;

import com.ijimenez.junit.junit5_app.eceptions.DineroInsuficienteEception;

import java.math.BigDecimal;

public class Cuenta {
    private String persona;
    private BigDecimal saldo;

    private Banco banco;

    public Cuenta() {
    }

    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona = persona;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void debito(BigDecimal monto) {
        //this.saldo = this.saldo.subtract(monto);
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new DineroInsuficienteEception("Dinero insuficiente");
        }

        this.saldo = nuevoSaldo;
    }

    public void credito(BigDecimal monto) {
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object obj) {//comparacion de tipo objeto
        Cuenta c = (Cuenta) obj;//hacemos el cast
        if (obj == null || !(obj instanceof Cuenta)) {//que no sea nullo y se valida que el objeto sea una instancia de Cuenta
            return false;
        }
        if (this.persona == null || this.saldo == null) {//validamos que el objeto sea diferente de null y saldo
            return false;//no son iguales
        }
        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }

    public int suma(int a, int b) {
        int suma = a + b;
        return suma;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
}
