CREATE DATABASE IF NOT EXISTS provincias_verdes;
USE provincias_verdes;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVO'
);

CREATE TABLE IF NOT EXISTS ubicaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    provincia VARCHAR(100) NOT NULL,
    codigo_postal VARCHAR(10),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS equipos_solares (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    potencia_nominal DOUBLE NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    estado VARCHAR(30) DEFAULT 'ACTIVO',
    fecha_instalacion DATE,
    id_ubicacion INT,
    FOREIGN KEY (id_ubicacion) REFERENCES ubicaciones(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS registros_energia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    produccion_kwh DOUBLE NOT NULL,
    consumo_kwh DOUBLE NOT NULL,
    id_equipo INT,
    FOREIGN KEY (id_equipo) REFERENCES equipos_solares(id) ON DELETE CASCADE
);
