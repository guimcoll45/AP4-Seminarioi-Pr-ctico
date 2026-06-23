CREATE DATABASE IF NOT EXISTS provincias_verdes
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE provincias_verdes;

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contraseña_hash VARCHAR(255) NOT NULL,
    tipo ENUM('ADMIN', 'USUARIO') NOT NULL,
    estado BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS ubicaciones (
    id_ubicacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    provincia VARCHAR(50) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    latitud DECIMAL(10,6) NOT NULL,
    longitud DECIMAL(10,6) NOT NULL,
    altitud DECIMAL(10,2),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS equipos_solares (
    id_equipo INT AUTO_INCREMENT PRIMARY KEY,
    id_ubicacion INT NOT NULL UNIQUE,
    tipo ENUM('PANEL', 'INVERSOR', 'BATERIA') NOT NULL,
    potencia_nominal DECIMAL(8,2) NOT NULL,
    cantidad INT NOT NULL,
    fecha_instalacion DATE NOT NULL,
    FOREIGN KEY (id_ubicacion) REFERENCES ubicaciones(id_ubicacion) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS registros_energia (
    id_registro BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_equipo INT NOT NULL,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    energia_generada_kwh DECIMAL(10,3) NOT NULL DEFAULT 0,
    energia_consumida_kwh DECIMAL(10,3) NOT NULL DEFAULT 0,
    voltaje DECIMAL(5,2),
    corriente DECIMAL(5,2),
    fuente VARCHAR(50),
    FOREIGN KEY (id_equipo) REFERENCES equipos_solares(id_equipo) ON DELETE CASCADE
);
