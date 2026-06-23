public String calcularBalance(double generada, double consumida) {
    double balance = generada - consumida;
    if (balance > 0) return "Excedente: " + balance + " kWh";
    else if (balance < 0) return "Déficit: " + Math.abs(balance) + " kWh";
    else return "Balance neutro";
}
