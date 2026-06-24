package com.provinciasverdes.interfaz;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EntradaDatos {
    private static Scanner sc = new Scanner(System.in);

    public static int leerEntero(String mensaje) {
        int dato;
        while (true) {
            try {
                System.out.print(mensaje);
                dato = sc.nextInt();
                sc.nextLine();
                return dato;
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Debe ingresar un número entero.");
                sc.nextLine();
            }
        }
    }

    public static double leerDouble(String mensaje) {
        double dato;
        while (true) {
            try {
                System.out.print(mensaje);
                dato = sc.nextDouble();
                sc.nextLine();
                return dato;
            } catch (InputMismatchException e) {
                System.out.println("❌ Error: Debe ingresar un número válido.");
                sc.nextLine();
            }
        }
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }
}
