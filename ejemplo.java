// Ordenar usuarios alfabéticamente
Collections.sort(listaUsuarios, Comparator.comparing(Usuario::getNombre));

// Ordenar mediciones de mayor a menor generación
Collections.sort(listaMediciones, Comparator.comparingDouble(RegistroEnergia::getGenerada).reversed());
