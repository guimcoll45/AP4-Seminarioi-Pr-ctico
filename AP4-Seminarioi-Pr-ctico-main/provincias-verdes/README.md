# ⚡ Provincias Verdes — Sistema de Gestión Energética Solar

**Sistema de escritorio desarrollado en Java con conexión a base de datos MySQL**, diseñado para administrar instalaciones solares, registrar mediciones de energía y generar informes de rendimiento.

---

## 📋 Funcionalidades principales
- Registro y autenticación de usuarios con distintos perfiles de acceso
- Gestión de ubicaciones y equipos solares
- Carga y almacenamiento de mediciones de energía generada y consumida
- Registro de voltaje y corriente en cada medición
- Cálculo automático de balance energético y eficiencia del sistema
- Generación de reportes en formato `.txt` y `.csv`
- Respaldo y recuperación de datos en archivos de texto

---

## 🛠️ Tecnologías utilizadas
- **Lenguaje**: Java 17 o superior
- **Base de datos**: MySQL 8.x
- **Conector**: MySQL Connector/J 9.x
- **Patrón de diseño**: DAO (Data Access Object)
- **Arquitectura**: Separación en capas:
  - `modelo`: Clases de entidad y enumeraciones
  - `negocio`: Lógica de cálculo y validaciones
  - `persistencia`: Conexión y acceso a la base de datos
  - `interfaz`: Menús y entrada de datos
  - `archivos`: Gestión de respaldos y exportaciones
  - `interfaces`: Definición de contratos para las clases

---

## 🚀 Pasos para ejecutar el proyecto

### 1. Requisitos previos
- Tener instalado **JDK 17+**
- Tener instalado **MySQL Server**
- Tener configurada la variable de entorno `JAVA_HOME`

### 2. Crear la base de datos
- Ejecutá el script que está en la carpeta `sql/provincias_verdes.sql` en tu gestor de MySQL (phpMyAdmin, MySQL Workbench, etc.)
- Esto creará la base `provincias_verdes` y todas las tablas necesarias

### 3. Configurar la conexión
- En el archivo `src/com/provinciasverdes/persistencia/ConexionDB.java` verifica los datos:
```java
private static final String URL = "jdbc:mysql://localhost:3306/provincias_verdes";
private static final String USUARIO = "tu_usuario";
private static final String CLAVE = "tu_contraseña";

###4. Compilar y ejecutar
Desde la carpeta raíz del proyecto:

# Compilar
javac -cp "lib/mysql-connector-j-9.x.jar" -d bin src/com/provinciasverdes/**/*.java

# Ejecutar
java -cp "bin;lib/mysql-connector-j-9.x.jar" com.provinciasverdes.interfaz.Main

##📁 Estructura del proyecto
provincias-verdes/
├── lib/                     # Librería de conexión a MySQL
│   └── mysql-connector-j-9.x.jar
├── sql/                     # Script para crear la base de datos
│   └── provincias_verdes.sql
├── src/                     # Código fuente Java
│   └── com/provinciasverdes/
│       ├── archivos/        # Gestión de respaldos de datos
│       ├── interfaz/        # Menús y entrada de datos por consola
│       ├── interfaces/      # Interfaces para exportación y validación
│       ├── modelo/          # Clases de entidad y enumeraciones
│       │   └── enums/       # Tipos y estados definidos
│       ├── negocio/         # Lógica de cálculo y reglas del sistema
│       └── persistencia/    # Conexión a la base y acceso a datos
└── README.md                # Documentación del proyecto

##👤 Usuario de prueba
Para iniciar sesión y probar el sistema:
Correo: admin@prov.com
Contraseña: 1234
