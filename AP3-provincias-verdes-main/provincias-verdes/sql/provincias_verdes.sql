CREATE DATABASE IF NOT EXISTS provincias_verdes;
USE provincias_verdes;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(50) NOT NULL,
    perfil VARCHAR(20) NOT NULL CHECK (perfil IN ('ADMIN', 'USUARIO'))
);

CREATE TABLE IF NOT EXISTS ubicaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    provincia VARCHAR(50) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    latitud DOUBLE NOT NULL,
    longitud DOUBLE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS equipos_solares (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_ubicacion INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    potencia DOUBLE NOT NULL,
    modelo VARCHAR(100),
    FOREIGN KEY (id_ubicacion) REFERENCES ubicaciones(id)
);

CREATE TABLE IF NOT EXISTS registros_energia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_equipo INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    voltaje DOUBLE,
    corriente DOUBLE,
    energia_generada DOUBLE DEFAULT 0,
    energia_consumida DOUBLE DEFAULT 0,
    FOREIGN KEY (id_equipo) REFERENCES equipos_solares(id)
);

-- Usuario de prueba
INSERT INTO usuarios (nombre, correo, contrasena, perfil) 
VALUES ('Administrador', 'admin@prov.com', '1234', 'ADMIN');
