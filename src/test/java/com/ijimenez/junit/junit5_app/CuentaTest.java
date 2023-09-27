package com.ijimenez.junit.junit5_app;

import com.ijimenez.junit.junit5_app.eceptions.DineroInsuficienteEception;
import com.ijimenez.junit.junit5_app.models.Banco;
import com.ijimenez.junit.junit5_app.models.Cuenta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {
    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Isael", new BigDecimal("12.45647987"));
        //cuenta.setPersona("Isael");

        String esperado = "Isael";
        String real = cuenta.getPersona();
        assertNotNull(real);
        assertEquals(esperado, real);
        assertTrue(real.equals(esperado));
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Isael", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);//cero es menor que 0
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);//cero es menor que 0
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("Susan", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Susan", new BigDecimal("8900.9997"));
        //assertNotEquals(cuenta2, cuenta);//comparacion por referencia o por instancia
        assertEquals(cuenta2, cuenta);//comparacion por valor pero hay un error el cual se soluciona con el equals en Cuenta
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Isael", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));//lo que se va a retirar de la cuenta
        assertNotNull(cuenta.getSaldo());//verificamos que el saldo no sea nullo
        assertEquals(900, cuenta.getSaldo().intValue());//compara el valor entero
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());////compara el valor en texto plano
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Isael", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));//lo que se va a tranferir a la cuenta
        assertNotNull(cuenta.getSaldo());//verificamos que el saldo no sea nullo
        assertEquals(1100, cuenta.getSaldo().intValue());//compara el valor entero
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());////compara el valor en texto plano
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Isael", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteEception.class, () -> {
            cuenta.debito(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero insuficiente";

        assertEquals(esperado, actual);
    }

    @Test
    void testSuma() {
        Cuenta cuenta = new Cuenta();
        int actual = cuenta.suma(5, 6);
        int esperado = 11;
        assertEquals(actual, esperado);
    }

    @Test
    void testTranferirDineroCuentas() {
        Cuenta cuenta = new Cuenta("Isael", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Susan", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Bando del bienestar");
        banco.tranferir(cuenta2, cuenta, new BigDecimal(500));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta.getSaldo().toPlainString());

    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta = new Cuenta("Isael", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Susan", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta2);

        banco.setNombre("Bando del bienestar");
        banco.tranferir(cuenta2, cuenta, new BigDecimal(500));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta.getSaldo().toPlainString());

        assertEquals(2, banco.getCuentas().size());

        assertEquals("Bando del bienestar", cuenta.getBanco().getNombre());

        assertEquals("Isael", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Isael"))
                .findFirst()
                .get().getPersona());

        assertTrue(banco.getCuentas().stream()
                .filter(c -> c.getPersona().equals("Isael"))
                .findFirst().isPresent());
    }
}