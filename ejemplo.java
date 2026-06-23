try {
    System.out.print("Ingrese el voltaje: ");
    double voltaje = scanner.nextDouble();
} catch (InputMismatchException e) {
    System.out.println("❌ Valor inválido. Debe ingresar un número.");
    scanner.next(); // Limpia el valor incorrecto del búfer
}
