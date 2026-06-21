package com.provinciasverdes.interfaz;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase auxiliar para manejar entradas del usuario.
 * Cumple RNF4: Manejo de excepciones InputMismatchException.
 */
public class EntradaDatos {
    private static Scanner sc = new Scanner(System.in);

    // Leer entero con validación
    public static int leerEntero(String mensaje) {
        int numero = 0;
        boolean valido = false;
        while(!valido) {
            try {
                System.out.print(mensaje);
                numero = sc.nextInt();
                sc.nextLine(); // Limpiar buffer
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada inválida. Ingrese un número entero.");
                sc.nextLine();
            }
        }
        return numero;
    }

    // Leer Double con validación (arregla problema de punto/coma)
    public static double leerDouble(String mensaje) {
        double numero = 0;
        boolean valido = false;
        while(!valido) {
            try {
                System.out.print(mensaje);
                String entrada = sc.nextLine().replace(",", ".");
                numero = Double.parseDouble(entrada);
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("❌ Entrada inválida. Ingrese
