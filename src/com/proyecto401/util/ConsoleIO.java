package com.proyecto401.util;

import java.util.Scanner;

public final class ConsoleIO {

    private ConsoleIO() {} // evita instanciar

    public static int pedirInt(Scanner sc, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Introduce un número entero válido.");
            }
        }
    }

    public static double pedirDouble(Scanner sc, String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Introduce un número decimal válido.");
            }
        }
    }

    public static String pedirString(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    // Para XQuery strings con comillas simples
    public static String escapeApos(String s) {
        return s.replace("'", "''");
    }
}
