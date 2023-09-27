package com.ijimenez.junit.junit5_app.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String nombre;

    private List<Cuenta> cuentas;

    public Banco() {
        cuentas = new ArrayList<>();
    }


    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void tranferir(Cuenta origen, Cuenta destino, BigDecimal mont) {
        origen.debito(mont);
        destino.credito(mont);
    }

    public void addCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
        cuenta.setBanco(this);
    }
}
