package com.ijimenez.junit.junit5_app;

import com.ijimenez.junit.junit5_app.eceptions.DineroInsuficienteEception;
import com.ijimenez.junit.junit5_app.models.Banco;
import com.ijimenez.junit.junit5_app.models.Cuenta;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {
    Cuenta cuenta;

    @BeforeEach
    void intMetodoTest() {
        this.cuenta = new Cuenta("Isael", new BigDecimal("12.45647987"));
        System.out.println("Iniciando el metodo. ");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando el metodo prueba");
    }

    @Test
    @DisplayName("probando el nombre de la cuenta correinte!")
    void testNombreCuenta() {
        //Cuenta cuenta = new Cuenta("Isael", new BigDecimal("12.45647987"));
        //cuenta.setPersona("Isael");

        String esperado = "Isael";
        String real = cuenta.getPersona();
        assertNotNull(real, () -> "La cuenta no puede ser nula");
        assertEquals(esperado, real, () -> "El nombre de la cuenta no es el que se esperaba: se esperaba " + esperado + " sin embargo fue: " + real);
        assertTrue(real.equals(esperado), () -> "nombre cuenta esperada debe ser igual a la regal");
    }

    @Test
    @DisplayName("probando el saldo de la cuenta correinte, que no sea null y mayor que cero, valor esperado")
    void testSaldoCuenta() {
        //Cuenta cuenta = new Cuenta("Isael", new BigDecimal("1000.12345"));
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);//cero es menor que 0
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);//cero es menor que 0
    }

    @Test
    @DisplayName("testeando referencias que sean iguales con el metodo equals.")
    void testReferenciaCuenta() {
        cuenta = new Cuenta("Susan", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("Susan", new BigDecimal("8900.9997"));
        //assertNotEquals(cuenta2, cuenta);//comparacion por referencia o por instancia
        assertEquals(cuenta2, cuenta);//comparacion por valor pero hay un error el cual se soluciona con el equals en Cuenta
    }

    @Test
    void testDebitoCuenta() {
        //Cuenta cuenta = new Cuenta("Isael", new BigDecimal("1000.12345"));
        this.cuenta.debito(new BigDecimal(100));//lo que se va a retirar de la cuenta
        assertNotNull(cuenta.getSaldo());//verificamos que el saldo no sea nullo
        assertEquals(900, cuenta.getSaldo().intValue());//compara el valor entero
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());////compara el valor en texto plano
    }

    @Test
    void testCreditoCuenta() {
        //Cuenta cuenta = new Cuenta("Isael", new BigDecimal("1000.12345"));
        this.cuenta.credito(new BigDecimal(100));//lo que se va a tranferir a la cuenta
        assertNotNull(cuenta.getSaldo());//verificamos que el saldo no sea nullo
        assertEquals(1100, cuenta.getSaldo().intValue());//compara el valor entero
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());////compara el valor en texto plano
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        //Cuenta cuenta = new Cuenta("Isael", new BigDecimal("1000.12345"));
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
    //@Disabled
    @DisplayName("probando relaciones entre las cuentas y el banco con assertAll.")
    void testRelacionBancoCuentas() {
        //fail(); //simula un error
        Cuenta cuenta = new Cuenta("Isael", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Susan", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del bienestar");
        banco.tranferir(cuenta2, cuenta, new BigDecimal(500));

        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(), () -> "El valor de la cuenta2 no es el esperado"),
                () -> assertEquals("3000", cuenta.getSaldo().toPlainString(), () -> "El valor de la cuenta no es el esperado"),
                () -> assertEquals(2, banco.getCuentas().size(), () -> "El banco no tiene las cuentas esperadas"),
                () -> assertEquals("Banco del bienestar", cuenta.getBanco().getNombre(), () -> "El nombre del banco no es el esperado"),
                () -> assertEquals("Isael", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Isael"))
                        .findFirst()
                        .get().getPersona(), () -> "No se encontro el nombre"),
                () -> assertTrue(banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Isael"))
                        .findFirst().isPresent(), () -> "No se encontro el nombre")
        );
    }
}