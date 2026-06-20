\# ☀️SISTEMA PROVINCIAS VERDES

\*\*Sistema de Gestión y Monitoreo de Energía Solar Renovable\*\*



Proyecto desarrollado en Java con conexión a base de datos MySQL, diseñado para administrar ubicaciones, equipos solares, registrar mediciones de energía y generar reportes de eficiencia y balance energético.



\---



\## 📋 CARACTERÍSTICAS PRINCIPALES

✅ Gestión de usuarios (Administradores y Usuarios)

✅ Registro de ubicaciones geográficas

✅ Administración de equipos solares

✅ Carga de mediciones de energía (generada y consumida)

✅ Cálculo automático de balance energético

✅ Generación de reportes por periodo

✅ Herramientas de búsqueda y ordenamiento

✅ Validaciones y control de errores



\---



\## 🗂️ ESTRUCTURA DEL PROYECTO

provincias-verdes/

├── 📁 src/

│ └── 📁 com/

│ └── 📁 provinciasverdes/

│ ├── 📁 modelo/ # Clases de entidad (Usuario, Ubicacion, etc.)

│ ├── 📁 persistencia/ # Conexión a BD y operaciones CRUD (DAO)

│ ├── 📁 negocio/ # Lógica de negocio (cálculos, reportes)

│ └── 📁 interfaz/ # Menú principal y ejecución

├── 📁 lib/ # Librerías externas (MySQL Connector)

├── 📁 bin/ # Archivos compilados (se genera al compilar)

└── 📄 README.md # Este archivo



\---



\## ⚙️ TECNOLOGÍAS UTILIZADAS

\- \*\*Lenguaje:\*\* Java 26

\- \*\*Base de Datos:\*\* MySQL

\- \*\*Conector:\*\* mysql-connector-java

\- \*\*Patrón de Diseño:\*\* MVC (Modelo - Vista - Controlador) y DAO



\---



\## 🚀 INSTALACIÓN Y CONFIGURACIÓN



\### 1. REQUISITOS PREVIOS

\- Tener instalado \*\*JDK 26\*\* o superior

\- Tener instalado \*\*MySQL\*\* (o XAMPP)

\- Tener el archivo `mysql-connector-java.jar` dentro de la carpeta `lib/`



\### 2. BASE DE DATOS

1\. Abrir tu gestor de MySQL (Workbench, phpMyAdmin, consola)

2\. Ejecutar el siguiente script para crear la base y las tablas:



```sql

CREATE DATABASE provinciasverdes;

USE provinciasverdes;



\\\\-- Tabla de Usuarios

CREATE TABLE usuarios (

\\\&#x20;   id INT AUTO\\\\\\\_INCREMENT PRIMARY KEY,

\\\&#x20;   nombre VARCHAR(100) NOT NULL,

\\\&#x20;   correo VARCHAR(100) NOT NULL UNIQUE,

\\\&#x20;   contrasena VARCHAR(50) NOT NULL,

\\\&#x20;   perfil VARCHAR(20) NOT NULL

);



\\\\-- Tabla de Ubicaciones

CREATE TABLE ubicaciones (

\\\&#x20;   id INT AUTO\\\\\\\_INCREMENT PRIMARY KEY,

\\\&#x20;   id\\\\\\\_usuario INT NOT NULL,

\\\&#x20;   provincia VARCHAR(100) NOT NULL,

\\\&#x20;   direccion VARCHAR(200) NOT NULL,

\\\&#x20;   latitud DOUBLE NOT NULL,

\\\&#x20;   longitud DOUBLE NOT NULL,

\\\&#x20;   FOREIGN KEY (id\\\\\\\_usuario) REFERENCES usuarios(id)

);



\\\\-- Tabla de Equipos Solares

CREATE TABLE equipos\\\\\\\_solares (

\\\&#x20;   id INT AUTO\\\\\\\_INCREMENT PRIMARY KEY,

\\\&#x20;   id\\\\\\\_ubicacion INT NOT NULL,

\\\&#x20;   tipo VARCHAR(50) NOT NULL,

\\\&#x20;   potencia DOUBLE NOT NULL,

\\\&#x20;   modelo VARCHAR(100) NOT NULL,

\\\&#x20;   FOREIGN KEY (id\\\\\\\_ubicacion) REFERENCES ubicaciones(id)

);



\\\\-- Tabla de Registros de Energía

CREATE TABLE registros\\\\\\\_energia (

\\\&#x20;   id INT AUTO\\\\\\\_INCREMENT PRIMARY KEY,

\\\&#x20;   id\\\\\\\_equipo INT NOT NULL,

\\\&#x20;   fecha\\\\\\\_hora DATETIME NOT NULL,

\\\&#x20;   voltaje DOUBLE NOT NULL,

\\\&#x20;   corriente DOUBLE NOT NULL,

\\\&#x20;   energia\\\\\\\_generada DOUBLE NOT NULL,

\\\&#x20;   energia\\\\\\\_consumida DOUBLE NOT NULL,

\\\&#x20;   FOREIGN KEY (id\\\\\\\_equipo) REFERENCES equipos\\\\\\\_solares(id)

);



\\\\-- Usuario administrador por defecto

INSERT INTO usuarios (nombre, correo, contrasena, perfil) 

VALUES ('Administrador', 'admin@prov.com', '1234', 'ADMIN');



▶️ COMPILACIÓN Y EJECUCIÓN

1\\\\. COMPILAR

Abrí la terminal en la carpeta raíz del proyecto y ejecutá:

"C:\\\\\\\\Program Files\\\\\\\\Java\\\\\\\\jdk-26.0.1\\\\\\\\bin\\\\\\\\javac" -cp "lib/\\\\\\\*" -d bin src/com/provinciasverdes/modelo/\\\\\\\*.java src/com/provinciasverdes/persistencia/\\\\\\\*.java src/com/provinciasverdes/negocio/\\\\\\\*.java src/com/provinciasverdes/interfaz/\\\\\\\*.java

2\\\\. EJECUTAR

java -cp "lib/\\\\\\\*;bin" com.provinciasverdes.interfaz.Main



🔑 ACCESO AL SISTEMA

Al iniciar, ingresá estas credenciales por defecto:

✉️ Correo: admin@prov.com

🔒 Contraseña: 1234

📖 GUÍA DE USO (ORDEN OBLIGATORIO)

Para que los datos se generen correctamente y no haya errores, seguí este orden:

📍 Opción 2: Gestión de Ubicaciones

Agregá una ubicación (ej: Formosa, Centro 123, lat: -26.17, lon: -58.18)

Guardá el ID que te devuelve

☀️ Opción 3: Gestión de Equipos Solares

Agregá un equipo usando el ID de ubicación anterior

Guardá el ID del equipo

⚡ Opción 4: Registro de Mediciones

Cargá mediciones usando el ID del equipo

Repetí varias veces para tener datos

📊 Opciones 5 y 6: Balance y Reportes

Ahora sí verás cálculos y reportes completos (ej: Junio-2026)



POSIBLES ERRORES Y SOLUCIONES

❌ No existe la base de datos: Ejecutá el script SQL primero.

❌ No se encuentra la tabla: Verificá que creaste todas las tablas correctamente.

❌ ID no existe: Respetá el orden: Ubicación → Equipo → Medición.

❌ Sin datos para calcular: Cargá al menos una medición antes de generar reportes.



