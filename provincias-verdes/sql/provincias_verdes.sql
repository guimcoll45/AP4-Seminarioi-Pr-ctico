-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS provincias_verdes
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE provincias_verdes;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    tipo_perfil ENUM('ADMIN', 'RESIDENCIAL', 'COMERCIAL') NOT NULL DEFAULT 'RESIDENCIAL',
    estado ENUM('ACTIVO', 'INACTIVO', 'SUSPENDIDO') NOT NULL DEFAULT 'ACTIVO',
    INDEX idx_correo (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de ubicaciones
CREATE TABLE IF NOT EXISTS ubicaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    provincia VARCHAR(50) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    latitud DECIMAL(10, 7) NOT NULL,
    longitud DECIMAL(10, 7) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    INDEX idx_usuario (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de equipos solares
CREATE TABLE IF NOT EXISTS equipos_solares (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_ubicacion INT NOT NULL,
    tipo ENUM('PANEL_SOLAR', 'INVERSOR', 'BATERIA', 'REGULADOR') NOT NULL,
    potencia DECIMAL(6, 2) NOT NULL COMMENT 'Potencia en kW',
    modelo VARCHAR(100) NOT NULL,
    fecha_instalacion DATETIME NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO', 'MANTENIMIENTO', 'FALLA') NOT NULL DEFAULT 'ACTIVO',
    FOREIGN KEY (id_ubicacion) REFERENCES ubicaciones(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    INDEX idx_ubicacion (id_ubicacion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de registros de energía
CREATE TABLE IF NOT EXISTS registros_energia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_equipo INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    voltaje DECIMAL(6, 2) NOT NULL COMMENT 'Voltaje en Voltios',
    corriente DECIMAL(6, 2) NOT NULL COMMENT 'Corriente en Amperios',
    energia_generada DECIMAL(8, 2) NOT NULL COMMENT 'Energía en kWh',
    energia_consumida DECIMAL(8, 2) NOT NULL COMMENT 'Energía en kWh',
    FOREIGN KEY (id_equipo) REFERENCES equipos_solares(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    INDEX idx_equipo (id_equipo),
    INDEX idx_fecha (fecha_hora)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Opcional: Insertar un usuario administrador inicial para probar
INSERT INTO usuarios (nombre, correo, contrasena, tipo_perfil, estado)
VALUES (
    'Administrador',
    'admin@provinciasverdes.com',
    'admin123',
    'ADMIN',
    'ACTIVO'
) ON DUPLICATE KEY UPDATE correo = correo;
