package com.provinciasverdes.interfaz;

import java.util.Scanner;

public class EntradaDatos {
    private static final Scanner sc = new Scanner(System.in);

    public static String leerCadena(String mensaje) {
        System.out.print(mensaje + ": ");
        return sc.nextLine().trim();
    }

    public static int leerEntero(String mensaje) {
        int valor;
        while (true) {
            System.out.print(mensaje + ": ");
            if (sc.hasNextInt()) {
                valor = sc.nextInt();
                sc.nextLine();
                return valor;
            }
            System.out.println("⚠️ Ingrese un número válido");
            sc.nextLine();
        }
    }

    public static double leerDoble(String mensaje) {
        double valor;
        while (true) {
            System.out.print(mensaje + ": ");
            if (sc.hasNextDouble()) {
                valor = sc.nextDouble();
                sc.nextLine();
                return valor;
            }
            System.out.println("⚠️ Ingrese un valor numérico válido");
            sc.nextLine();
        }
    }
}
