-- =============================================
-- BASE DE DATOS: provincias_verdes
-- CORREGIDO: nombre de tabla = usuarios
-- =============================================

DROP DATABASE IF EXISTS provincias_verdes;
CREATE DATABASE provincias_verdes
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE provincias_verdes;

-- 1. Tabla: ubicacion
CREATE TABLE IF NOT EXISTS ubicacion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    provincia VARCHAR(100) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    codigo_postal VARCHAR(20),
    latitud DOUBLE,
    longitud DOUBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Tabla: usuarios (AHORA EN PLURAL, como lo pide el programa)
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('ADMINISTRADOR', 'OPERADOR', 'CONSULTA') NOT NULL DEFAULT 'CONSULTA',
    estado ENUM('ACTIVO', 'INACTIVO', 'SUSPENDIDO') NOT NULL DEFAULT 'ACTIVO',
    id_ubicacion INT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_ubicacion) REFERENCES ubicacion(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Tabla: equipo_solar
CREATE TABLE IF NOT EXISTS equipo_solar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    tipo_equipo ENUM('PANEL', 'INVERSOR', 'BATERIA', 'REGULADOR') NOT NULL,
    potencia_nominal DOUBLE NOT NULL,
    voltaje_nominal DOUBLE NOT NULL,
    corriente_maxima DOUBLE,
    fecha_instalacion DATE,
    estado ENUM('ACTIVO', 'MANTENIMIENTO', 'FUERA_DE_SERVICIO') NOT NULL DEFAULT 'ACTIVO',
    id_ubicacion INT NOT NULL,
    id_responsable INT,
    FOREIGN KEY (id_ubicacion) REFERENCES ubicacion(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_responsable) REFERENCES usuarios(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Tabla: registro_energia
CREATE TABLE IF NOT EXISTS registro_energia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_equipo INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    voltaje DOUBLE NOT NULL,
    corriente DOUBLE NOT NULL,
    energia_generada DOUBLE NOT NULL,
    energia_consumida DOUBLE NOT NULL,
    balance DOUBLE NOT NULL,
    observaciones TEXT,
    FOREIGN KEY (id_equipo) REFERENCES equipo_solar(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Datos de prueba
INSERT INTO ubicacion (provincia, ciudad, direccion, codigo_postal)
VALUES ('Formosa', 'Formosa', 'Calle Principal 123', '3600');

-- Usuario con tus datos: admin@prov.com / 1234
INSERT INTO usuarios (nombre, apellido, correo, contrasena, tipo_usuario, estado, id_ubicacion)
VALUES ('Admin', 'Sistema', 'admin@prov.com', '1234', 'ADMINISTRADOR', 'ACTIVO', 1);

INSERT INTO equipo_solar (id, modelo, tipo_equipo, potencia_nominal, voltaje_nominal, fecha_instalacion, estado, id_ubicacion, id_responsable)
VALUES 
(1, 'Panel Solar 300W', 'PANEL', 300.0, 24.0, '2025-01-15', 'ACTIVO', 1, 1),
(2, 'Panel Solar 500W', 'PANEL', 500.0, 220.0, '2025-02-01', 'ACTIVO', 1, 1);